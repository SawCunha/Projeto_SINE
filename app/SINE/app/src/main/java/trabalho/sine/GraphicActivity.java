package trabalho.sine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import trabalho.sine.activity.FragmentDrawer;
import trabalho.sine.activity.LoadActivities;

public class GraphicActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    @BindView(R.id.graphic_web_view) WebView graphicWebView;
    @BindView(R.id.toolbar) Toolbar mToolbar;

    private FragmentDrawer mDrawerFragment;
    private int idfuncao;
    private String tipo_empresa;
    private String cargo;
    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphic);

        //Define o ButterKnife para gerenciar as activities e ativa o modo de debugação.
        ButterKnife.bind(this);
        ButterKnife.setDebug(true);

        createToolbar();

        //Obtém os valores da outra activity.
        getValues();

        // Adicionando o grafico
        webview = (WebView) this.findViewById(R.id.graphic_web_view);
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webview.requestFocusFromTouch();
        webview.setWebViewClient(new WebViewClient(){
            public void onPageFinished(WebView view, String url){
                webview.loadUrl("javascript:init("+idfuncao+",\""+tipo_empresa+"\" )");
                Log.d("MAURICIO",tipo_empresa);
            }
        });
        webview.setWebChromeClient(new WebChromeClient());
        webview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        webview.loadUrl("file:///android_asset/grafico/charts.html");
    }

    private void getValues(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        idfuncao = bundle.getInt("idfuncao");
        tipo_empresa = bundle.getString("tipo_empresa");
        cargo = bundle.getString("cargo");
    }

    private void createToolbar(){
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mDrawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer_graphic);
        mDrawerFragment.setUp(R.id.fragment_navigation_drawer_graphic, (DrawerLayout) findViewById(R.id.activity_graphic), mToolbar);
        mDrawerFragment.setDrawerListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        switch (position){
            case 0: LoadActivities.home(this); break;
            case 1: LoadActivities.searchActivity(this); break;
            case 2: LoadActivities.favoriteActivity(this); break;
            case 3: LoadActivities.searchForGraphicActivity(this);break;
            case 4: LoadActivities.info(this); break;
            default: Log.i("ERRO","POSITION ERROR"); break;
        }
    }
}
