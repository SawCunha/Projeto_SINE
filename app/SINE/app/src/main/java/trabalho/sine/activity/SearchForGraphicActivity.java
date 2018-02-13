package trabalho.sine.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NavUtils;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import trabalho.sine.R;
import trabalho.sine.adapter.CargoSuggestionAdapter;
import trabalho.sine.model.Cargo;
import trabalho.sine.utils.Constantes;
import trabalho.sine.utils.NavigationSine;

public class SearchForGraphicActivity extends AppCompatActivity {

    @BindView(R.id.cargo_search_edit_text)
    AutoCompleteTextView cargoSearchEditText;
    @BindView(R.id.porte_radio_group)
    RadioGroup radioGroup;
    private DrawerLayout drawerLayout;
    private Resources resources;
    private Cargo cargo;
    private String valueRadioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_graphic);

        ButterKnife.bind(this);
        ButterKnife.setDebug(true);
        initTextView();

        cargoSearchEditText.setAdapter(new CargoSuggestionAdapter(this, cargoSearchEditText.getText().toString(), Constantes.URL_API + "/idfuncao/"));

        //Obtém as informações do cargo selecionado pelo usuário.
        cargoSearchEditText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                cargo = (Cargo) adapterView.getItemAtPosition(position);
            }
        });

        createNavigationView();
    }

    private void createNavigationView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout_search_for_graphic);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationSine(drawerLayout, R.id.searchForGraphicActivity, this));
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            NavUtils.navigateUpFromSameTask(this);
        }
    }

    //Gerencia os eventos da caixa de texto.
    private void initTextView() {
        resources = getResources();

        //Cria um objeto para poder exercutarmos operações nos campos editáveis.
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                callClearErrors(editable);
            }
        };
    }

    //Dispara o evento e exibe uma mensagem de erro caso o campo texto esteja vazio.
    private void callClearErrors(Editable editable) {
        if (!editable.toString().isEmpty()) clearErrorFields(cargoSearchEditText);
    }

    //Valida dos dados dos campos de texto.
    private boolean validateFields() {
        if (TextUtils.isEmpty(cargoSearchEditText.getEditableText().toString().trim()) || cargo == null) {
            cargoSearchEditText.requestFocus();
            cargoSearchEditText.setError(resources.getString(R.string.campo_cargo_vazio));
            return false;
        }
        return true;
    }

    //Limpa as mensagens de erro dos campos de texto da activity.
    private void clearErrorFields(EditText... editTexts) {
        for (EditText editText : editTexts) editText.setError(null);
    }

    @OnClick(R.id.search_button)
    public void pesquisarCargo() {
        validateFields();
    }

    //Realiza a chamada a outra activity, enviando o id da função para que seja gerado o gráfico.
    @OnClick(R.id.search_button)
    public void getAverages() {
        if (!validateFields()) return;

        Intent graphicIntent = new Intent(SearchForGraphicActivity.this, ChartsActivity.class);
        Bundle bundle = new Bundle();

        bundle.putInt("idfuncao", cargo.getId().intValue());
        bundle.putString("cargo", cargo.getDescricao());

        int selectedId = radioGroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        RadioButton radioButton = findViewById(selectedId);


        bundle.putString("tipo_empresa", radioButton.getText().toString());
        graphicIntent.putExtras(bundle);

        startActivity(graphicIntent);
    }//getAverages()
}//class SearchForGraphicActivity
