package trabalho.sine.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.VolleyError;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import trabalho.sine.R;
import trabalho.sine.controller.RequestURL;
import trabalho.sine.model.MediaSalariaJSON;
import trabalho.sine.model.MediaSalarial;
import trabalho.sine.utils.Constantes;
import trabalho.sine.utils.Functions;

public class ChartsActivity extends AppCompatActivity {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.chart)
    BarChart barChart;

    private int idfuncao;
    private String tipo_empresa;
    private String cargo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        barChart.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getValues();
        setTitle(cargo);
        obtemChartValues();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_return, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.returnMenu) onBackPressed();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
    }

    // obtem todas as vagas da api.
    @SuppressLint("DefaultLocale")
    private void obtemChartValues() {
        RequestURL req = new RequestURL(this);

        req.requestURL(String.format(Constantes.URL_API_CHARTS_VALUES, idfuncao), new RequestURL.VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                MediaSalariaJSON vagasJSON = gson.fromJson(response, MediaSalariaJSON.class);
                if (tipo_empresa.equals(getString(R.string.pequeno_porte_label)))
                    _initChart(vagasJSON.getMedia_salarial().getSalarios().getPequena_empresa());
                else if (tipo_empresa.equals(getString(R.string.medio_porte_label)))
                    _initChart(vagasJSON.getMedia_salarial().getSalarios().getPequena_empresa());
                else if (tipo_empresa.equals(getString(R.string.grande_porte_label)))
                    _initChart(vagasJSON.getMedia_salarial().getSalarios().getPequena_empresa());

            }

            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    Log.e("Error", error.getMessage());
                } catch (Exception e) {
                    Functions.DialogErro(ChartsActivity.this, "Não foi possivel obter a Media Salarial.");
                    e.printStackTrace();
                    NavUtils.navigateUpFromSameTask(ChartsActivity.this);
                }
            }
        });

    }

    private void _initChart(MediaSalarial mediaSalarial) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(mediaSalarial.getTrainne(), 0));
        entries.add(new BarEntry(mediaSalarial.getJunior(), 1));
        entries.add(new BarEntry(mediaSalarial.getPleno(), 2));
        entries.add(new BarEntry(mediaSalarial.getSenior(), 3));
        entries.add(new BarEntry(mediaSalarial.getMaster(), 4));

        ArrayList<String> labels = new ArrayList<>();
        labels.add("Trainer");
        labels.add("Junior");
        labels.add("Pleno");
        labels.add("Aenior");
        labels.add("Master");

        BarDataSet dataset = new BarDataSet(entries, "Salarios");

        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        ArrayList<BarDataSet> dataSets = new ArrayList<>();  // combined all dataset into an arraylist<br />
        dataSets.add(dataset);

        BarData data = new BarData(labels, dataSets); // initialize the Bardata with argument labels and dataSet<br />

        barChart.setData(data);
        barChart.setDescription(cargo + " - " + tipo_empresa);

        progressBar.setVisibility(View.INVISIBLE);
        barChart.setVisibility(View.VISIBLE);

    }

    private void getValues() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        assert bundle != null;
        idfuncao = bundle.getInt("idfuncao");
        tipo_empresa = bundle.getString("tipo_empresa");
        cargo = bundle.getString("cargo");
    }
}
