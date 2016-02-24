package webacademy.ua.freelancehunt_beta;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
/**
 * Created by Назар on 24.02.2016.
 */
public class GETTask extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... params) {
        HttpUriRequest request = new HttpGet(url2);
        String credentials = api_token + ":" +sing(api_secret2,url2,method2,"");
        String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        Log.e("Login: Response", base64EncodedCredentials);
        request.addHeader("Authorization", "Basic " + base64EncodedCredentials);

        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response = null;
        try {
            response = httpclient.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            final HttpEntity entity = response.getEntity();
            if (entity == null) {
                Log.w("no", "The response has no entity.");
            } else {
                Log.e("Response from server:",GetText(entity.getContent()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
