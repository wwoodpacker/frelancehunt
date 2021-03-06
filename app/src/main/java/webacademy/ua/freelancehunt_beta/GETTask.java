package webacademy.ua.freelancehunt_beta;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
    private ProgressDialog pDialog;
    public GETTask(MainActivity activity) {
        pDialog = new ProgressDialog(activity);
    }
    @Override
    protected String doInBackground(String... params) {
        Log.e("Asynctask", "doinback");

        HttpUriRequest request = new HttpGet(params[2]);
        Sign SIGN= new Sign();
        String otvet="";
        String credentials = params[0] + ":" +SIGN.sing(params[1],params[2],params[3],"");
        String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        Log.e("Login: Response", base64EncodedCredentials);
        request.addHeader("Authorization", "Basic " + base64EncodedCredentials);

        HttpClient httpclient = new DefaultHttpClient();

        HttpResponse response = null;
        try {
            response = httpclient.execute(request);
            try {
                final HttpEntity entity = response.getEntity();
                if (entity == null) {
                    Log.e("no", "The response has no entity.");
                } else {
                    otvet=SIGN.GetText(entity.getContent());
                    Log.e("Response from server:",otvet );
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("Error", "response null");
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Error", "not connection ");

            //Toast.makeText(MainActivity.class,"Not connection",Toast.LENGTH_SHORT).show();
        }

        return otvet;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog.setMessage("Getting Data ...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
        Log.e("Asynctask", "onpostexec");
    }
}
