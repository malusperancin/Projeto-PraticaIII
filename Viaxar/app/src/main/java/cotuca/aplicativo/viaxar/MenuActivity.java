package cotuca.aplicativo.viaxar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.HashMap;

import cotuca.aplicativo.viaxar.dbos.Usuario;

public class MenuActivity extends AppCompatActivity {

    TextView nome, email;
    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawerLayout;
    private int id= 1;
    private Usuario usuario;
    SessionManager session;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        session = new SessionManager(this);
        session.checkLogin();

        /*SIDE NAV*/
        Toolbar toolbar = findViewById(R.id.tb_titulo);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.side_nav_menu);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_info, R.id.nav_fav, R.id.nav_kit, R.id.nav_exit)
                .setDrawerLayout(drawerLayout)
                .build();
        final NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.nav_exit)
                session.logout();
                return NavigationUI.onNavDestinationSelected(item, navController);
            }
        });

         /* BOTTOM NAV */
        BottomNavigationView navView = findViewById(R.id.nav_bottom);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_explore,R.id.nav_world, R.id.nav_fav, R.id.nav_info)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_info)
                    switchDrawer(drawerLayout);
                else
                    return NavigationUI.onNavDestinationSelected(item, navController);
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        HashMap<String, String> user = session.getUserDetail();
        try {
            usuario = new Usuario(Integer.parseInt(user.get(session.ID)), user.get(session.EMAIL) ,user.get(session.CELULAR));
        }
        catch(Exception ex){}

        View headerView = navigationView.getHeaderView(0);
        TextView nome = (TextView) headerView.findViewById(R.id.nome);
        TextView email = (TextView) headerView.findViewById(R.id.email);
        nome.setText(usuario.getNome());
        email.setText(usuario.getEmail());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void switchDrawer(DrawerLayout drawer)
    {
        if(drawer.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            drawer.openDrawer(GravityCompat.START);
    }
}