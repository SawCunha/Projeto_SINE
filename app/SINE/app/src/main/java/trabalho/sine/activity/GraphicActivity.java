package trabalho.sine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import trabalho.sine.R;
import trabalho.sine.utils.NavigationSine;

public class GraphicActivity extends AppCompatActivity{

    @BindView(R.id.graphic_web_view) WebView graphicWebView;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;

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

        createNavigationView();

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
                if(tipo_empresa == null || tipo_empresa.isEmpty()) tipo_empresa = "Pequena";
                webview.loadUrl("javascript:init("+idfuncao+",\""+tipo_empresa+"\" )");
            }
        });
        webview.setWebChromeClient(new WebChromeClient());
        webview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        webview.loadUrl("file:///android_asset/grafico/charts.html");
    }

    private void createNavigationView(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout_graphic);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationSine(drawerLayout,-1,this));
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void getValues(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        idfuncao = bundle.getInt("idfuncao");
        tipo_empresa = bundle.getString("tipo_empresa");
        cargo = bundle.getString("cargo");
    }
}
