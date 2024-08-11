package app.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Result {
    private String expression;
    private BigDecimal result;
    private LocalDateTime dateTime;

    public Result(String expression, BigDecimal result, LocalDateTime dateTime) {
        this.expression = expression;
        this.result = result;
        this.dateTime = dateTime;
        System.out.println(dateTime);
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public BigDecimal getResult() {
        return result;
    }

    public void setResult(BigDecimal result) {
        this.result = result;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Expression: " + this.expression + ", Result: " + this.result + ", Date: " + this.dateTime.getDayOfMonth() +
                ". " + this.dateTime.getMonth() + " " + this.dateTime.getYear() + "., Time: " + this.dateTime.getHour() + ":" + this.dateTime.getMinute();
    }
}
