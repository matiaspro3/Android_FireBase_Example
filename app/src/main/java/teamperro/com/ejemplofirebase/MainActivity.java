package teamperro.com.ejemplofirebase;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {

    private String FIREBASE_URL = "https://appperro-3e95e.firebaseio.com/";
    private String FIREBASE_CHILD = "descripcion";
    @Bind(R.id.editText) EditText editText;
    Firebase firebase;
    Button _play;
    int _idSonido;
    SoundPool _sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Firebase.setAndroidContext(this);
        firebase = new Firebase(FIREBASE_URL).child(FIREBASE_CHILD);

        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                Toast.makeText(MainActivity.this, snapshot.getValue().toString(), Toast.LENGTH_SHORT).show();

                Log.e(getLocalClassName(), snapshot.getValue().toString());

            }
            @Override
            public void onCancelled(FirebaseError error) {
            }
        });
            	//asd

        _play = (Button) findViewById(R.id.button2);
        _sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 1);
        _idSonido = _sp.load(this, R.raw.sonidoperro, 1);

        _play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Reproduciendo audio", Toast.LENGTH_SHORT).show();
                _sp.play(_idSonido, 1f, 1f, 1, 0, 1f);//.play nos pide el int de nuestra canción, un //int float de volumen tanto derecho como izquierdo que van de 0.0=0% a 1.0=100%, el int de loop //donde -1 es reproducción en loop, 0 reproduce una vez y 1 repite sólo una vez, y por último tenemos //un int de Rate que va de 0.5 a 2 y modifica la velocidad de pitch donde 1.0 seria la velicidad normal

                //Branch
            }
        });
    }

    @OnClick(R.id.button)
    public void writeToFirebase() {
        firebase.setValue(editText.getText().toString());
        editText.setText("");

    }
}
