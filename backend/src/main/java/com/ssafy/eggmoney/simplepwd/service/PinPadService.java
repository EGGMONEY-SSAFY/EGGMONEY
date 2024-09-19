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

    public PinPadService() throws IOException {
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
        List<Integer> pinPad = IntStream.range(0, 10).boxed().collect(Collectors.toList());
        Collections.shuffle(pinPad);

        // Create image for pin pad
        BufferedImage pinPadImage = createPinPadImage(pinPad);
        byte[] imageBytes = convertImageToBytes(pinPadImage);
        String encodedImage = Base64.getEncoder().encodeToString(imageBytes);
//
        return new PinPadResponse(pinPad, encodedImage);

//        return new PinPadResponse(pinPad, imageBytes);
    }

    private BufferedImage createPinPadImage(List<Integer> pinPad) {
        int imageSize = 400;
        int cellSize = imageSize / 4;
        BufferedImage pinPadImage = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = pinPadImage.createGraphics();

        // 배경을 투명하게 설정
        graphics.setComposite(AlphaComposite.Clear);
        graphics.fillRect(0, 0, imageSize, imageSize);
        graphics.setComposite(AlphaComposite.SrcOver);

        // 숫자의 위치를 설정합니다 (3x4 배열)
        int padding = 10;
        for (int i = 0; i < pinPad.size(); i++) {
            int number = pinPad.get(i);
            BufferedImage numberImage = numberImages.get(number);
            Image scaledImage = numberImage.getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH);

            int x, y;

            if (i < 9) { // 숫자가 상단에 배치되는 경우
                x = (i % 3) * (cellSize + padding);
                y = (i / 3) * (cellSize + padding);
            } else { // 숫자가 하단에 배치되는 경우
                x = (imageSize - cellSize) / 2;
                y = 3 * (cellSize + padding);
            }

            graphics.drawImage(scaledImage, x, y, null);
        }

        // 뒤로가기와 확인 버튼을 적절한 위치에 배치
        graphics.drawImage(backImage, 0, 3 * (cellSize + padding), cellSize, cellSize, null);
        graphics.drawImage(checkImage, 3 * (cellSize + padding), 3 * (cellSize + padding), cellSize, cellSize, null);

        graphics.dispose();
        return pinPadImage;
    }

    private byte[] convertImageToBytes(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return baos.toByteArray();
    }
}
