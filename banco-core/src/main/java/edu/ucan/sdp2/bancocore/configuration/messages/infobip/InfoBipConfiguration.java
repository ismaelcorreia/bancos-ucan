package edu.ucan.sdp2.bancocore.configuration.messages.infobip;

import com.infobip.ApiClient;
import com.infobip.ApiException;
import com.infobip.ApiKey;
import com.infobip.BaseUrl;
import com.infobip.api.SmsApi;
import com.infobip.model.SmsAdvancedTextualRequest;
import com.infobip.model.SmsDestination;
import com.infobip.model.SmsTextualMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@Slf4j
public class InfoBipConfiguration {

    private final ApiClient apiClient;
    private final SmsApi senderApi;

    private final InfoBipProperties properties;
    @Autowired
    public InfoBipConfiguration(InfoBipProperties infoBipProperties){
        this.properties = infoBipProperties;
        apiClient = ApiClient
                .forApiKey(ApiKey.from(properties.getApiKey()))
                .withBaseUrl(BaseUrl.from(properties.getBaseUrl()))
                .build();

        senderApi = new SmsApi(apiClient);
    }
    public void prepareAndSend(String phoneDestination, String smsContent){

        // Create a message to send.
        var smsMessage = new SmsTextualMessage()
                .addDestinationsItem(new SmsDestination().to(phoneDestination))
                .text(smsContent);
        var smsMessageRequest = new SmsAdvancedTextualRequest()
                .messages(Collections.singletonList(smsMessage));

        try {
            // Send the message.
            var smsResponse = senderApi.
                    sendSmsMessage(smsMessageRequest).execute();
           log.info("Response body: {}", smsResponse);
            var reportsResponse = senderApi.getOutboundSmsMessageDeliveryReports().execute();
            log.info("response json: {}", reportsResponse.getResults());
        } catch (ApiException e) {
            log.error("HTTP status code: {}", e.responseStatusCode());
            log.error("Response body: {}", e.rawResponseBody());
        }
    }
}
