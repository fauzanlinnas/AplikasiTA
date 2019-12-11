package com.example.serius;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.sql.DataSource;

import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

public class WekaActivity extends AppCompatActivity {

    private static final String WEKA_TEST = "WekaTest";

    private Random mRandom = new Random();

    private Pendonor[] mPendonor = new Pendonor[] {
            new Pendonor(1,1, new double[]{2,50,98}),
            new Pendonor(2,1, new double[]{0,13,28}),
            new Pendonor(3,0, new double[]{1,24,77})
    };

    private Classifier mClassifier = null;

    TextView mTextViewSamples = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weka);

        // show samples
        StringBuilder stringBuilder = new StringBuilder("Samples:\n");
        for(Pendonor s : mPendonor) {
            stringBuilder.append(s.toString() + "\n");
        }
        mTextViewSamples = findViewById(R.id.textViewSamplesWeka);
        mTextViewSamples.setText(stringBuilder.toString());

        Log.d(WEKA_TEST, "onCreate() finished.");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public void wekaLoadModel(View _v) {
        AssetManager assetManager = getAssets();
        try {
            mClassifier = (Classifier) weka.core.SerializationHelper.read(assetManager.open("donordarah.model"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // Weka "catch'em all!"
            e.printStackTrace();
        }
        Toast.makeText(this, "Model loaded.", Toast.LENGTH_SHORT).show();
    }

    public void wekaPredict(View _v) {
        Log.d(WEKA_TEST, "onClickButtonPredict()");

        if(mClassifier==null){
            Toast.makeText(this, "Model not loaded!", Toast.LENGTH_SHORT).show();
            return;
        }

        // we need those for creating new instances later
        // order of attributes/classes needs to be exactly equal to those used for training
        final Attribute attributeRecency = new Attribute("recency");
        final Attribute attributeFrequency = new Attribute("frequency");
        final Attribute attributeTime = new Attribute("time");
        final List<String> classes = new ArrayList<String>() {
            {
                add("tidak donor"); // cls nr 1
                add("donor"); // cls nr 2
            }
        };

        // Instances(...) requires ArrayList<> instead of List<>...
        ArrayList<Attribute> attributeList = new ArrayList<Attribute>(2) {
            {
                add(attributeRecency);
                add(attributeFrequency);
                add(attributeTime);
                Attribute attributeClass = new Attribute("@@class@@", classes);
                add(attributeClass);
            }
        };
        // unpredicted data sets (reference to sample structure for new instances)
        Instances dataUnpredicted = new Instances("TestInstances",
                attributeList, 1);
        // last feature is target variable
        dataUnpredicted.setClassIndex(dataUnpredicted.numAttributes() - 1);

        // create new instance: this one should fall into the setosa domain
        final Pendonor s = mPendonor[mRandom.nextInt(mPendonor.length)];
        DenseInstance newInstance = new DenseInstance(dataUnpredicted.numAttributes()) {
            {
                setValue(attributeRecency, s.features[0]);
                setValue(attributeFrequency, s.features[1]);
                setValue(attributeTime, s.features[2]);
            }
        };
        // reference to dataset
        newInstance.setDataset(dataUnpredicted);

        // predict new sample
        try {
            double result = mClassifier.classifyInstance(newInstance);
            String className = classes.get(new Double(result).intValue());
            String msg = "Nr: " + s.nr + ", predicted: " + className + ", actual: " + classes.get(s.label);
            Log.d(WEKA_TEST, msg);
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class Pendonor {
        public int nr;
        public int label;
        public double [] features;

        public Pendonor(int _nr, int _label, double[] _features) {
            this.nr = _nr;
            this.label = _label;
            this.features = _features;
        }

        @Override
        public String toString() {
            return "Nr " +
                    nr +
                    ", cls " + label +
                    ", feat: " + Arrays.toString(features);
        }
    }
}
