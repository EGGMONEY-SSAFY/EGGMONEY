package com.ssafy.eggmoney.stock.entity;

import com.ssafy.eggmoney.common.entity.BaseTime;
import com.ssafy.eggmoney.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Table(name = "stock_users")
@NoArgsConstructor(access = PROTECTED)
public class StockUser extends BaseTime {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "stock_user_id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @NotNull
    private int buyAverage;

    @NotNull
    private int amount;

    public StockUser(User user, Stock stock, int buyAverage, int amount) {
        this.user = user;
        this.stock = stock;
        this.buyAverage = buyAverage;
        this.amount = amount;
    }

    public void buyStock(int price, int amount) {
        BigDecimal totalPrice = BigDecimal.valueOf(this.buyAverage * this.amount + price * amount);
        this.amount += amount;
        this.buyAverage = totalPrice.divide(BigDecimal.valueOf(this.amount), 0, RoundingMode.HALF_UP)
                .intValue();
    }

    public void sellStock(int amount) {
        if(this.amount - amount > 0) {
            this.amount -= amount;
        } else if(this.amount - amount == 0) {
            this.amount = 0;
            this.buyAverage = 0;
        } else {
            throw new IllegalArgumentException("판매하려는 주식 수가 보유 주식 수를 초과합니다.");
        }
    }
}
