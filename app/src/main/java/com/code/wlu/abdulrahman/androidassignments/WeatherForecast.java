package com.code.wlu.abdulrahman.androidassignments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WeatherForecast extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "WeattherForecast";
    public ProgressBar pb;

    TextView tvCurrentTemperature, tvMin, tvMax;
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

        fq = new ForecastQuery();

        fq.execute();
        Log.i(ACTIVITY_NAME,"WF oncreate");

    }


    class ForecastQuery extends AsyncTask<String, String, String> {



        public String ns = null;
        String MIN = "", MAX = "", CURRENT_TEMPERATURE = "";
        Bitmap bmpWEATHER;

        TextView tvTemperature;

        List<Entry> weather;

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
            super.onPostExecute(s);
            Entry output = weather.get(0);

            tvCurrentTemperature.setText("Temperature :- " + output.value);
            tvMax.setText("Maximum temperature :-" + output.max);
            tvMin.setText("Minimum temperature :-" + output.min);

            pb.setVisibility(View.VISIBLE);
        }

        public List parse(InputStream in) throws XmlPullParserException, IOException {
            try
            {
                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(in, null);
                parser.nextTag();
                return readFeed(parser);

            } finally {
                in.close();
            }
        }

        private List readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
            List entries = new ArrayList();

            parser.require(XmlPullParser.START_TAG, ns, "current");
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                // Starts by looking for the entry tag
                if (name.equals("temperature")) {
                    entries.add(readEntry(parser));
                } else {
                    skip(parser);
                }
            }
            return entries;
        }

        private Entry readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
            String ns = null;
            parser.require(XmlPullParser.START_TAG, ns, "temperature");
            String value = null;
            String min = null;
            String max = null;

            String name = parser.getName();
            value = parser.getAttributeValue(null, "value");
            publishProgress("25");
            min = parser.getAttributeValue(null, "min");
            publishProgress("50");
            max = parser.getAttributeValue(null, "max");
            publishProgress("75");
            return new Entry(value, min, max);
        }



        private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                throw new IllegalStateException();
            }
            int depth = 1;
            while (depth != 0) {
                switch (parser.next()) {
                    case XmlPullParser.END_TAG:
                        depth--;
                        break;
                    case XmlPullParser.START_TAG:
                        depth++;
                        break;
                }
            }
        }

        public class Entry {
            public final String value;
            public final String min;
            public final String max;

            private Entry(String value, String min, String max) {
                this.value = value;
                this.min = min;
                this.max = max;
            }
        }
    }
}