package trabalho.sine;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import trabalho.sine.activity.FragmentDrawer;
import trabalho.sine.activity.LoadActivities;
import trabalho.sine.adapter.CargoSuggestionAdapter;
import trabalho.sine.model.Cargo;
import trabalho.sine.utils.Constantes;

public class SearchForGraphicActivity extends AppCompatActivity implements  FragmentDrawer.FragmentDrawerListener{

    private Toolbar mToolbar;
    private FragmentDrawer mDrawerFragment;
    private Resources resources;

    @BindView(R.id.cargo_search_edit_text) AutoCompleteTextView cargoSearchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_graphic);

        ButterKnife.bind(this);
        ButterKnife.setDebug(true);
        initTextView();

        cargoSearchEditText.setAdapter(new CargoSuggestionAdapter(this, cargoSearchEditText.getText().toString(), Constantes.URL_API + "/idfuncao/"));

        cargoSearchEditText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Cargo c = (Cargo) adapterView.getItemAtPosition(position);
                Toast.makeText(SearchForGraphicActivity.this,"ID: " + c.getId(), Toast.LENGTH_SHORT).show();
            }
        });

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        createToolbar();
    }

    private void initTextView(){
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

    private void callClearErrors(Editable editable){
        if(!editable.toString().isEmpty()) clearErrorFields(cargoSearchEditText);
    }

    private boolean validateFields(){
        if (TextUtils.isEmpty(cargoSearchEditText.getEditableText().toString().trim())){
            cargoSearchEditText.requestFocus();
            cargoSearchEditText.setError(resources.getString(R.string.campo_cargo_vazio));
            return false;
        }
        return true;
    }

    private void clearErrorFields(EditText... editTexts){
        for (EditText editText : editTexts) editText.setError(null);
    }

    @OnClick(R.id.search_button)
    public void pesquisarCargo(){
        validateFields();
    }

    //Responsavel pela criação e definção do toolbar
    private void createToolbar(){
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mDrawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer_graphic);
        mDrawerFragment.setUp(R.id.fragment_navigation_drawer_graphic, (DrawerLayout) findViewById(R.id.activity_graphic), mToolbar);
        mDrawerFragment.setDrawerListener(this);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        switch (position){
            case 0: LoadActivities.home(this); break;
            case 1: LoadActivities.searchActivity(this); break;
            case 2: LoadActivities.favoriteActivity(this); break;
            case 3: break;
            case 4: LoadActivities.info(this); break;
            default: Log.i("ERRO","POSITION ERROR"); break;
        }
    }
}
