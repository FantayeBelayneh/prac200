package com.code.wlu.abdulrahman.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherForecast extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "WeatherForecast";
    public ProgressBar pb;

    TextView tvCurrentTemperature, tvMin, tvMax;
    ImageView image;
    Bitmap bitmapFile;
    ForecastQuery fq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        pb = findViewById(R.id.progressBar);
        pb.setProgress(0);

        pb.setVisibility(View.VISIBLE);

        tvCurrentTemperature = findViewById(R.id.tvCurrentTemperature);
        tvMax = findViewById(R.id.tvMaxTemperature);
        tvMin = findViewById(R.id.tvMinTemperature);
        image = findViewById(R.id.imageView3);

        fq = new ForecastQuery();

        fq.execute();
        Log.i(ACTIVITY_NAME,"WF oncreate");

    }


    class ForecastQuery extends AsyncTask<String, String, String> {

        public String ns = null;
        String MIN = "", MAX = "", CURRENT_TEMPERATURE = "";
        Bitmap bmpWEATHER;

        TextView tvTemperature;

        Entry weather;

        @Override
        protected String doInBackground(String... strings) {

            String urlString;
            urlString = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=b496b4aa8c828e32eadbe3312d00b186&mode=xml&units=metric";

            try {
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                weather = parse(conn.getInputStream());

                /*try {
                    InputStream in = new java.net.URL(urldisplay).openStream();
                    bitmapFile = BitmapFactory.decodeStream(in);-

                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }*/



            } catch (MalformedURLException m) {
                Log.i(ACTIVITY_NAME + "_m", m.getMessage().toString());
            } catch (IOException io) {
                Log.i(ACTIVITY_NAME + "_io", io.getMessage().toString());
            } catch (XmlPullParserException XX) {
                Log.i(ACTIVITY_NAME + "_XX", XX.getMessage().toString());
            }

            return "dummy";
        }

        @Override
        protected void onProgressUpdate(String... values)
        {
            super.onProgressUpdate(values);
            int progress_mark= Integer.parseInt(values[0]);
            pb.setProgress(progress_mark);

            // this was added to see the progress in action as it was unnoticeable
            try {
                Thread.sleep(500);
            }
            catch ( InterruptedException x)
            {
                Log.i(ACTIVITY_NAME + "_while updating", x.getMessage().toString());
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
                pb.setMax(100);
                pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s)
        {
            tvCurrentTemperature.setText("Temperature :- " + weather.value  +  "  icon " + weather.icon);
            tvMax.setText("Maximum temperature :-" + weather.max);
            tvMin.setText("Minimum temperature :-" + weather.min);
            pb.setVisibility(View.VISIBLE);
            /*try {
                Log.i(ACTIVITY_NAME + " creating file", "before function call");


                Log.i(ACTIVITY_NAME + " creating file", "after function call");
            } catch (IOException e) {
                Log.i(ACTIVITY_NAME + " creating file", e.getMessage().toString());
                e.printStackTrace();
            }*/
        }

        public Entry parse(InputStream in) throws XmlPullParserException, IOException {

            try
            {
                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(in, null);
                parser.nextTag();

                return readTemperature(parser);

            } finally {
                in.close();
            }
        }

        private Entry readTemperature(XmlPullParser parser) throws XmlPullParserException, IOException {

            String value = null;
            String min = null;
            String max = null;
            String icon = null;

            boolean d= false;
            Entry result = new Entry();
            parser.require(XmlPullParser.START_TAG, ns, "current");
            int type;
            String name;
            while ((type = parser.getEventType()) != XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() == XmlPullParser.START_TAG)
                {
                    name= parser.getName();
                    Log.i(ACTIVITY_NAME + "in read Feed", "parsed value = " + name);
                    if (name.equals("temperature"))
                    {
                        result.value = parser.getAttributeValue(null, "value");
                        publishProgress("25");
                        result.min = parser.getAttributeValue(null, "min");
                        publishProgress("50");
                        result.max = parser.getAttributeValue(null, "max");
                        publishProgress("75");
                    }
                    else if (name.equals("weather"))
                    {
                        result.icon = parser.getAttributeValue(null, "icon");
                    }
                }
                parser.next();
            }
            return result;
        }

        public Bitmap CreateImage(String ImageURL, String iconName) throws IOException {
            String fname = iconName + ".png" ;
            Bitmap image = null;
            File file = getBaseContext().getFileStreamPath(fname);

            if (!file.exists())
            {
                Log.i(ACTIVITY_NAME + " creating file", "file " + file.getName().toString() + " exists");
                image = getImage(fname);

            }
            else
            {
                Log.i(ACTIVITY_NAME + " creating file", " file creation begins.");
                FileOutputStream outputStream = openFileOutput( iconName + ".png", Context.MODE_PRIVATE);
                image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                outputStream.flush();
                outputStream.close();
                Log.i(ACTIVITY_NAME + " creating file", " completed file creation");
            }
            return image;
        }




        public  Bitmap getImage(URL url) {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    return BitmapFactory.decodeStream(connection.getInputStream());
                } else
                    return null;
            } catch (Exception e) {
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }
        public Bitmap getImage(String urlString)
        {
            try
            {
                URL url = new URL(urlString);
                return getImage(url);
            }
            catch (MalformedURLException e)
            {
                return null;
            }
        }



        public class Entry {
            public String value =null;
            public String min =null;
            public String max = null;
            public String icon=null;

        }
    }
}