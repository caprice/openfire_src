package wetodo.manager;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

public class PayManager {

    private static final String IAP_API_URL = "https://sandbox.itunes.apple.com/verifyReceipt";
    private static final String ENCODING = "UTF-8";
    private static PayManager instance;

    /**
     * 单例产生实例
     *
     * @return
     */
    public static PayManager getInstance() {
        if (instance == null) {
            synchronized (PayManager.class) {
                instance = new PayManager();
            }
        }
        return instance;
    }

    public static String inputStreamToString(InputStream inputStream) throws IOException {
        StringWriter writer = new StringWriter();
        IOUtils.copy(inputStream, writer, PayManager.ENCODING);
        return writer.toString();
    }

    public void purchase() {
        String response = null;
        try {
            response = this.request();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(response);
    }

    public String request() throws IOException {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost postRequest = new HttpPost(
                PayManager.IAP_API_URL);

        StringEntity input = new StringEntity("{\"receipt-data\":\"eyJpdGVtX2lkIjoiMzcxMjM1IiwgIm9yaWdpbmFsX3RyYW5zYWN0aW9uX2lkIjoiMTAxMjMwNyIsICJidnJzIjoiMS4wIiwgInByb2R1Y3RfaWQiOiJjb20uZm9vLmN1cCIsICJwdXJjaGFzZV9kYXRlIjoiMjAxMC0wNS0yNSAyMTowNTozNiBFdGMvR01UIiwgInF1YW50aXR5IjoiMSIsICJiaWQiOiJjb20uZm9vLm1lc3NlbmdlciIsICJvcmlnaW5hbF9wdXJjaGFzZV9kYXRlIjoiMjAxMC0wNS0yNSAyMTowNTozNiBFdGMvR01UIiwgInRyYW5zYWN0aW9uX2lkIjoiMTEyMzcifQ==\"}");
        input.setContentType("application/json");
        postRequest.setEntity(input);

        HttpResponse response = httpClient.execute(postRequest);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            InputStream inputstream = entity.getContent();
            try {
                String responseBody = PayManager.inputStreamToString(inputstream);
                return responseBody;
            } finally {
                inputstream.close();
            }
        }
        return null;
    }

}
