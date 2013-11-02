package wetodo.sms;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.SmsFactory;
import com.twilio.sdk.resource.instance.Sms;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class SMS {
    public static final String ACCOUNT_SID = "ACa407f913ac3f6f16dad71138e9189e99";
    public static final String AUTH_TOKEN = "007d1cb428ec52c9e2423d12041b2199";
    public static final String FROM = "+14844986260";
    public static String msg = "Your Code is {0}";

    public static void send(String phone, String countryCode, long code) throws TwilioRestException {

        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
        String phoneWithCountryCode = countryCode + phone;

        Map<String, String> params = new HashMap<String, String>();
        params.put("Body", MessageFormat.format(msg, String.valueOf(code)));
        params.put("To", phoneWithCountryCode);
        params.put("From", FROM);

        SmsFactory messageFactory = client.getAccount().getSmsFactory();
        Sms message = messageFactory.create(params);
        System.out.println(message.getSid());
    }
}
