package cotuca.aplicativo.viaxar;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.HashMap;

import cotuca.aplicativo.viaxar.R;
import cotuca.aplicativo.viaxar.dbos.Usuario;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class InformationsFragment extends Fragment {

    Usuario usuario = null;
    EditText edtEmail = null;
    EditText edtCell = null;
    SessionManager session;
    HashMap<String, String> user;
    EditText edtOld;
    EditText edtPass;
    EditText edtPassConfirm;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_informations, container, false);

        final Button btnAlterarSenha = (Button) root.findViewById(R.id.btnAlterarSenha);
        edtOld =  (EditText) root.findViewById(R.id.edtOld);
        edtPass = (EditText) root.findViewById(R.id.edtPass);
        edtPassConfirm = (EditText) root.findViewById(R.id.edtPassConfirm);
        final Button btnSalvarTudo = (Button) root.findViewById(R.id.btnSalvarTudo);
        final Button btnSalvarSenha = (Button) root.findViewById(R.id.btnSalvarSenha);
        final LinearLayout layout = (LinearLayout) root.findViewById(R.id.lt_trocarSenha);
        edtEmail = (EditText) root.findViewById(R.id.edtEmail);
        edtCell = (EditText) root.findViewById(R.id.edtCell);
        session = new SessionManager(getContext());

        btnAlterarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtPass.getVisibility() == EditText.GONE) {
                    edtOld.setVisibility(EditText.VISIBLE);
                    edtPass.setVisibility(EditText.VISIBLE);
                    edtPassConfirm.setVisibility(EditText.VISIBLE);
                    btnSalvarSenha.setVisibility(EditText.VISIBLE);
                } else {
                    edtOld.setVisibility(EditText.GONE);
                    edtPass.setVisibility(EditText.GONE);
                    edtPassConfirm.setVisibility(EditText.GONE);
                    btnSalvarSenha.setVisibility(EditText.GONE);
                }
            }
        });

        user = session.getUserDetail();
        try {
            usuario = new Usuario(Integer.parseInt(user.get(session.ID)), user.get(session.EMAIL), user.get(session.CELULAR));
            edtEmail.setText(usuario.getEmail());
            if (usuario.getCelular() != null)
                edtCell.setText(usuario.getCelular());
        } catch (Exception ex) {
            Toast.makeText(this.getContext(), "erro", Toast.LENGTH_LONG).show();
        }

        btnSalvarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtPass.getText().toString().equals(edtPassConfirm.getText().toString())){
                    AlterarSenha();
                    edtOld.setText("");
                    edtPass.setText("");
                    edtPassConfirm.setText("");
                    btnAlterarSenha.callOnClick();
                }
                else
                    Toast.makeText(getContext(), "Senhas não coincidentes", Toast.LENGTH_LONG).show();
            }
        });

        btnSalvarTudo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtEmail.getText().toString() != null) {
                    try {
                        usuario.setEmail(edtEmail.getText().toString());
                        usuario.setCelular(edtCell.getText().toString());
                        AlterarUsuario();
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), "Invalid informations, try again", Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(getContext(), "Fill all blanks", Toast.LENGTH_LONG).show();
            }
        });
        return root;
    }

    public void AlterarSenha(){
        Usuario usuario = null;
        try {
            usuario = new Usuario(Integer.parseInt(user.get(session.ID)),edtEmail.getText().toString(), edtCell.getText().toString());
        } catch (Exception ex) {
            Toast.makeText(getContext(), "Fill all blanks", Toast.LENGTH_LONG).show();
            return;
        }

        Call call = new RetrofitConfig().getService().alterarSenha(usuario, edtOld.getText().toString(), edtPass.getText().toString());
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Response response, Retrofit retrofit) {
                if (response.isSuccess()) //  precisa do isSuccess se a gente der exceção no node
                    Toast.makeText(getContext(), "Senha trocada com sucesso", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getContext(), "Erro ao trocar senha", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getContext(), "Erro ao trocar senha", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void AlterarUsuario() {

        Usuario usuario = null;
        try {
            usuario = new Usuario(Integer.parseInt(user.get(session.ID)),edtEmail.getText().toString(), edtCell.getText().toString());
        } catch (Exception ex) {
            Toast.makeText(getContext(), "Fill all blanks", Toast.LENGTH_LONG).show();
            return;
        }

        Call call = new RetrofitConfig().getService().alterarUsuario(usuario);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Response response, Retrofit retrofit) {
                if (response.isSuccess()) //  precisa do isSuccess se a gente der exceção no node
                {
                    if (response.body() == null) {
                        session.editor.putString(session.EMAIL,edtEmail.getText().toString());
                        session.editor.putString(session.CELULAR,edtCell.getText().toString());
                        Toast.makeText(getContext(), "Usuario alterado no sucesso", Toast.LENGTH_LONG).show();
                    }
                    else
                        Toast.makeText(getContext(), "Erro ao dar update", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getContext(), "Erro ao dar update", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getContext(), "Ocorreu um erro na requisição", Toast.LENGTH_LONG).show();
            }
        });
    }
}