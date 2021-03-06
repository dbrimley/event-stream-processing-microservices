package demo.payment;

import demo.event.EventService;
import demo.event.Events;
import demo.event.PaymentEvent;
import demo.event.PaymentEventType;
import demo.payment.controller.PaymentController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PaymentController.class)
public class PaymentControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PaymentService paymentService;

    @MockBean
    private EventService<PaymentEvent, Long> eventService;

    @Test
    public void getUserPaymentResourceShouldReturnPayment() throws Exception {
        String content = "{\"paymentMethod\": \"CREDIT_CARD\", \"amount\": 42.0 }";

        Payment payment = new Payment(42.0, PaymentMethod.CREDIT_CARD);

        given(this.paymentService.getPayment(1L)).willReturn(payment);
        given(this.eventService.find(1L)).willReturn(new Events<>(1L, Collections
                .singletonList(new PaymentEvent(PaymentEventType
                        .PAYMENT_CREATED))));

        this.mvc.perform(get("/v1/payments/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(content));
    }
}
