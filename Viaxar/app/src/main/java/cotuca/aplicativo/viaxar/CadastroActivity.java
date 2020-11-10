package cotuca.aplicativo.viaxar;

import androidx.appcompat.app.AppCompatActivity;
import cotuca.aplicativo.viaxar.dbos.*;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CadastroActivity extends AppCompatActivity {

    TextView tvTelaLogin;
    EditText edtNome, edtSenha, edtEmail;
    Button btnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        btnSalvar = findViewById(R.id.btnCadastro);
        tvTelaLogin = findViewById(R.id.tvLogin);
        edtSenha = findViewById(R.id.edtSenhaCad);
        edtEmail = findViewById(R.id.edtEmailCad);


        tvTelaLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incluirUsuario();
            }
        });

    }
    public void incluirUsuario() {

        Usuario usuario = null;
        try{
            usuario = new Usuario(edtEmail.getText().toString(),
                    edtSenha.getText().toString());
        }
        catch (Exception ex){}

        if(usuario == null){
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_LONG).show();
            return;
        }

        Call call = new RetrofitConfig().getService().incluirUsuario(usuario);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Response response, Retrofit retrofit) {
                if(response.isSuccess()) //  precisa do isSuccess se a gente der exceção no node
                {
                    if(response.body() == null)
                    {
                        Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Email ja cadastrado", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getApplicationContext(), "Ocorreu um erro na inclusão", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getApplicationContext(), "Ocorreu um erro na requisição", Toast.LENGTH_LONG).show();
            }
        });
    }
}