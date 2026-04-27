package com.pharmadesk.backend.model;

import com.pharmadesk.backend.pharmacy.enums.PaymentMode;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_transactions")
public class PaymentTransaction extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_bill_id", nullable = false)
    private CreditBill creditBill;

    public CreditBill getCreditBill() { return creditBill; }
    public void setCreditBill(CreditBill creditBill) { this.creditBill = creditBill; }

    @Column(nullable = false)
    private BigDecimal amount;

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_mode", nullable = false)
    private PaymentMode paymentMode;

    public PaymentMode getPaymentMode() { return paymentMode; }
    public void setPaymentMode(PaymentMode paymentMode) { this.paymentMode = paymentMode; }

    @Column(name = "payment_date", nullable = false)
    private LocalDateTime paymentDate;

    public LocalDateTime getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDateTime paymentDate) { this.paymentDate = paymentDate; }

    @Column(name = "transaction_reference")
    private String transactionReference;

    public String getTransactionReference() { return transactionReference; }
    public void setTransactionReference(String transactionReference) { this.transactionReference = transactionReference; }
}
