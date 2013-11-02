package wetodo.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import wetodo.dao.PayDAO;
import wetodo.dao.ProductDAO;
import wetodo.dao.UserDAO;
import wetodo.error.IAPError;
import wetodo.exception.IapIdNotExistsException;
import wetodo.exception.ReceiptAlreadyExistsException;
import wetodo.exception.ReceiptIAPValidFailException;
import wetodo.model.Pay;
import wetodo.model.Product;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;

public class PayManager {

    private static final String IAP_API_URL = "https://sandbox.itunes.apple.com/verifyReceipt";
    private static final String ENCODING = "UTF-8";
    private static PayManager instance;

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

    public void purchase(String username, String receipt, String iapId) throws ReceiptAlreadyExistsException, ReceiptIAPValidFailException, IapIdNotExistsException {
        Pay pay = PayDAO.findByReceipt(receipt);
        if (pay != null) {
            throw new ReceiptAlreadyExistsException();
        } else {
            String response = this.request(receipt);
            System.out.println(response);

            JSONObject json = JSON.parseObject(response);
            if (json != null && json.containsKey("status") && (Integer) json.get("status") == IAPError.Condition.success.getCode()) {
                Pay payNew = new Pay();
                payNew.setUsername(username);
                payNew.setReceipt(receipt);
                payNew.setIap_id(iapId);
                Timestamp now = new Timestamp(System.currentTimeMillis());
                payNew.setCreate_date(now);
                payNew.setModify_date(now);

                PayDAO.create(payNew);

                Product product = ProductDAO.findByIapId(iapId);
                if (product == null) {
                    throw new IapIdNotExistsException();
                }
                UserDAO.increaseVip(username, product.getDay());
            } else {
                throw new ReceiptIAPValidFailException();
            }
        }
    }

    public String request(String receipt) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost postRequest = new HttpPost(
                PayManager.IAP_API_URL);

        InputStream inputstream = null;
        try {
            StringEntity input = new StringEntity("{\"receipt-data\":\"" + receipt + "\"}");
            input.setContentType("application/json");
            postRequest.setEntity(input);
            HttpResponse response = httpClient.execute(postRequest);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                inputstream = entity.getContent();
                String responseBody = PayManager.inputStreamToString(inputstream);
                return responseBody;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inputstream);
        }
        return null;
    }
}
