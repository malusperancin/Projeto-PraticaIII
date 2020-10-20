package cotuca.aplicativo.viaxar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cotuca.aplicativo.viaxar.dbos.Usuario;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class LoginActivity extends AppCompatActivity {

    TextView tvTelaCadastro;
    Button btnLogin;
    EditText edtEmail, edtSenha;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvTelaCadastro = findViewById(R.id.tvCadastro);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        edtEmail = findViewById(R.id.edtEmailCad);
        edtSenha = findViewById(R.id.edtSenhaCad);

        tvTelaCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
               // login(edtEmail.getText().toString(), edtSenha.getText().toString());
            }
        });
    }

    public void login(String email, String senha)
    {
        if (email == null || email.equals("") || senha == null || senha.equals(""))
        {
            Toast.makeText(this, "Insira o email e a senha", Toast.LENGTH_LONG).show();
            return;
        }

        Call<Usuario> call = new RetrofitConfig().getService().login(email, senha);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Response<Usuario> response, Retrofit retrofit) {
                if(response.isSuccess()) {
                    Usuario usuario = response.body();

                    if (usuario != null) {
                        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                        startActivity(intent);
                        finish();
                        //coisa a sessao
                    } else
                    {
                        Toast.makeText(getApplicationContext(), "Conta n enconytrada ou senha errada", Toast.LENGTH_LONG).show();
                    }
                }
                else
                    Toast.makeText(getApplicationContext(), "Erro ao buscar pelo usuario", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getApplicationContext(), "Ocorreu um erro na rede", Toast.LENGTH_LONG).show();
            }
        });
    }
}