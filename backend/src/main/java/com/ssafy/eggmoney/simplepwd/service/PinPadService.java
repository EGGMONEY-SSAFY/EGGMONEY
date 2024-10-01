package com.ssafy.eggmoney.simplepwd.service;

import com.ssafy.eggmoney.simplepwd.dto.response.PinPadResponse;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

@Service
public class PinPadService {
    private final Map<Integer, BufferedImage> numberImages;
    private final BufferedImage backImage;
    private final BufferedImage checkImage;
    private final EncryptionService encryptionService;


    public PinPadService(EncryptionService encryptionService) throws IOException {
        this.encryptionService = encryptionService;
        numberImages = IntStream.range(0, 10)
                .boxed()
                .collect(Collectors.toMap(
                        i -> i,
                        i->loadImage(i+".png")
                ));
        backImage = loadImage("back.png");
        checkImage = loadImage("check.png");
    }

    private BufferedImage loadImage(String filename) {
        try {
            Resource resource = new ClassPathResource("images/pinpad/" + filename);
            return ImageIO.read(resource.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("Failed to load image for number: " + filename, e);
        }
    }

    public PinPadResponse generatePinPadResponse() throws IOException {
        try{
            List<Integer> pinPad = IntStream.range(0, 10).boxed().collect(Collectors.toList());
            Collections.shuffle(pinPad);

            StringBuilder pinOrder = new StringBuilder();
            for (int number : pinPad) {
                pinOrder.append(number);
            }
            int lastIndex = pinPad.size() - 1;
            pinPad.add(lastIndex, -1); // back.png
            pinPad.add(-2); // check.png /
            BufferedImage pinPadImage = createPinPadImage(pinPad);
            byte[] imageBytes = convertImageToBytes(pinPadImage);
        String encodedImage = Base64.getEncoder().encodeToString(imageBytes);
            //String encodedImage = encryptionService.encryptImage(imageBytes);
            return new PinPadResponse(pinPad, encodedImage);
        }catch (Exception e) {
            throw new RuntimeException("Failed to generate PinPadResponse", e);
        }


//        return new PinPadResponse(pinPad, imageBytes);
    }

    private BufferedImage createPinPadImage(List<Integer> pinPad) {
        int imageSize = 393;
        int cellSize = imageSize / 4; // 4x3 그리드에 맞추기 위해 cellSize 설정
        BufferedImage pinPadImage = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = pinPadImage.createGraphics();

        // 투명한 배경 설정
        graphics.setComposite(AlphaComposite.Clear);
        graphics.fillRect(0, 0, imageSize, imageSize);
        graphics.setComposite(AlphaComposite.SrcOver);

        int padding = 0;

        // 모든 요소를 3x4 배열로 배치 (숫자 10개 + back, check 버튼)
        for (int i = 0; i < pinPad.size(); i++) {
            int number = pinPad.get(i);
            Image img;

            if (number == -1) {
                img = backImage;
            } else if (number == -2) {
                img = checkImage;
            } else {
                img = numberImages.get(number);
            }

            // 이미지 크기 조정
            Image scaledImage = img.getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH);

            // 좌표 계산
            int x = (i % 3) * (cellSize + padding) + 48;
            int y = (i / 3) * (cellSize + padding);

            // Image를 BufferedImage로 변환하여 그리기
            BufferedImage bufferedImage = toBufferedImage(scaledImage);
            graphics.drawImage(bufferedImage, x, y, null);
        }

        graphics.dispose();
        return pinPadImage;
    }

    // Image를 BufferedImage로 변환하는 메서드
    private BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        return bimage;
    }

    private byte[] convertImageToBytes(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return baos.toByteArray();
    }
}
