package tim.a37290700.mytime2exit;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import static java.lang.String.format;
import static java.lang.String.valueOf;

public class MainActivity extends AppCompatActivity {

    EditText OraInText, OraImText, OraFmText;
    TextView OraExitText,breakText;
    TimePickerDialog picker;
    RadioButton rdbFulltime, rdbPartTime;
    boolean started = false;    // disable the first OnFocusChange

    private class Orario {      // class for time management
        int Hour;
        int Minute;

        Orario(int hour, int minute) { //integer constructor
            Hour = hour;
            Minute = minute;
        }
        Orario(String time) { //String constructor  "12:30"
            Hour = Integer.parseInt(valueOf(time.substring(0, 2)));
            Minute = Integer.parseInt(valueOf(time.substring(3, 5)));
        }
         int getHours() {
            return Hour;
        }
         void setHours(int hour) {
            Hour = hour;
        }
         int getMinutes() {
            return Minute;
        }
         void setMinutes(int minute) {
            Minute = minute;
        }
    }

    Orario oredalavorare  = new Orario(7, 38);
    Orario orariouscita = new Orario(16,38);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Send a mail to: filippo.favorito@telecomitalia.it", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        OraExitText=(TextView)findViewById(R.id.TextView_ora_exit);
        breakText=(TextView)findViewById(R.id.TextViewIntervallo);
        fab.requestFocusFromTouch();
        OraInText=(EditText) findViewById(R.id.editText_ora_in);
        OraInText.setInputType(InputType.TYPE_NULL);
        OraInText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    String ora = OraInText.getText().toString();
                    int hour = Integer.parseInt(valueOf(ora.substring(0, 2)));  // cldr.get(Calendar.HOUR_OF_DAY);  // setta i valori a time attuali
                    int minutes = Integer.parseInt(valueOf(ora.substring(3, 5)));  // cldr.get(Calendar.MINUTE);
                    // time picker dialog
                    picker = new TimePickerDialog(MainActivity.this,
                            new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                    if (sHour>9){   // max time for entrance
                                        sHour=9;
                                        sMinute=30;
                                    }
                                    else if((sHour==9)&&(sMinute>30))   // max time for entrance
                                        sMinute=30;
                                    OraInText.setText(format("%02d:%02d", sHour, sMinute));
                                    CalcolaOraUscita();
                                }
                            }, hour, minutes, true);
                    if (started)
                        picker.show();
                    else
                        started= true;
                }
        };
        });
        OraInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ora = OraInText.getText().toString();
                int hour = Integer.parseInt(valueOf(ora.substring(0, 2)));
                int minutes = Integer.parseInt(valueOf(ora.substring(3, 5)));
                // time picker dialog
                picker = new TimePickerDialog(MainActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                if (sHour>9){   // max time for entrance
                                    sHour=9;
                                    sMinute=30;
                                }
                                else if((sHour==9)&&(sMinute>30))   // max time for entrance
                                    sMinute=30;
                                OraInText.setText(format("%02d:%02d", sHour, sMinute));
                                CalcolaOraUscita();
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });

        OraImText=(EditText) findViewById(R.id.editText_ora_im);
        OraImText.setInputType(InputType.TYPE_NULL);
        OraImText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    String ora = OraImText.getText().toString();
                    int hour = Integer.parseInt(valueOf(ora.substring(0, 2)));  // cldr.get(Calendar.HOUR_OF_DAY);  // setta i valori a time attuali
                    int minutes = Integer.parseInt(valueOf(ora.substring(3, 5)));  // cldr.get(Calendar.MINUTE);
                    // time picker dialog
                    picker = new TimePickerDialog(MainActivity.this,
                            new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                    OraImText.setText(format("%02d:%02d", sHour, sMinute));
                                    CalcolaOraUscita();//sHour + ":" + sMinute); --> String.format("%02d:%02d", sHour, sMinute);
                                }
                            }, hour, minutes, true);
                    if (started)
                        picker.show();
                    else
                        started= true;
                }
            };
        });
        OraImText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ora = OraImText.getText().toString();
                int hour = Integer.parseInt(valueOf(ora.substring(0, 2)));
                int minutes = Integer.parseInt(valueOf(ora.substring(3, 5)));
                // time picker dialog
                picker = new TimePickerDialog(MainActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                OraImText.setText(format("%02d:%02d", sHour, sMinute));
                                CalcolaOraUscita();
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });
        OraFmText=(EditText) findViewById(R.id.editText_ora_fm);
        OraFmText.setInputType(InputType.TYPE_NULL);
        OraFmText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    String ora = OraFmText.getText().toString();
                    int hour = Integer.parseInt(valueOf(ora.substring(0, 2)));  // cldr.get(Calendar.HOUR_OF_DAY);  // setta i valori a time attuali
                    int minutes = Integer.parseInt(valueOf(ora.substring(3, 5)));  // cldr.get(Calendar.MINUTE);
                    // time picker dialog
                    picker = new TimePickerDialog(MainActivity.this,
                            new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                    OraFmText.setText(format("%02d:%02d", sHour, sMinute));
                                    CalcolaOraUscita();//sHour + ":" + sMinute); --> String.format("%02d:%02d", sHour, sMinute);
                                }
                            }, hour, minutes, true);
                    if (started)
                        picker.show();
                    else
                        started= true;
                }
            };
        });
        OraFmText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ora = OraFmText.getText().toString();
                int hour = Integer.parseInt(valueOf(ora.substring(0, 2)));
                int minutes = Integer.parseInt(valueOf(ora.substring(3, 5)));
                // time picker dialog
                picker = new TimePickerDialog(MainActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                OraFmText.setText(format("%02d:%02d", sHour, sMinute));
                                CalcolaOraUscita();
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });
/*
            btnGet=(ImageButton)findViewById(R.id.imageButton);
            btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
        // check radioButton state
        rdbFulltime = findViewById(R.id.radioButtonFull);
        rdbFulltime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((RadioButton) v).isChecked();
                // Check which RadioButton was clicked
                if (checked){
                    // set time to full
                    oredalavorare.setHours(7);
                    oredalavorare.setMinutes(38);
                    orariouscita.setHours(16);
                    orariouscita.setMinutes(38);
                    CalcolaOraUscita();
                }
            }
        });
        rdbPartTime = findViewById(R.id.radioButtonPart);
        rdbPartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((RadioButton) v).isChecked();
                // Check which RadioButton was clicked
                if (checked){
                    // set time to full
                    oredalavorare.setHours(5);
                    oredalavorare.setMinutes(43);
                    orariouscita.setHours(14);
                    orariouscita.setMinutes(43);
                    CalcolaOraUscita();
                }
            }
        });
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            Toast.makeText(getApplicationContext(), "Time2Exit by Fil 23.1.19.02", Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.action_exit) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void CalcolaOraUscita() {
        // calcolo minuti di pausa e ora di uscita...
        int Intervallo = 0;
        int monteore =0;
        int monteminuti = 0;
        Orario oraingresso = new Orario(OraInText.getText().toString());
        Orario iniziomensa = new Orario(OraImText.getText().toString());    // values from timepicker
        Orario finemensa = new Orario(OraFmText.getText().toString());
        String str ="";
        Intervallo = ((finemensa.getHours() - iniziomensa.getHours()) * 60) + (finemensa.getMinutes() - iniziomensa.getMinutes());
        monteore = oraingresso.getHours()+oredalavorare.getHours();
        monteminuti = oraingresso.getMinutes()+oredalavorare.getMinutes();
        if (monteminuti > 59)
        {
            monteore += 1;
            monteminuti -= 60;
        }
        if (Intervallo > 60) {
            Intervallo = 60;
            str = "Attenzione! hai timbrato in ritardo!!";
            Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
        }
        else if (Intervallo < 30) {

            if (Intervallo < 25) {
                str = "Intervallo min. 25 minuti!!";
                Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
            }
            Intervallo = 30;
        }
        monteminuti += Intervallo;
        if (monteminuti > 59) {
            monteore += 1;
            monteminuti -= 60;
        }

        //$("#p_mensa").val();
        breakText.setText(format("%02d", Intervallo));
        if (monteore < orariouscita.getHours()) {
            monteore = orariouscita.getHours();
            monteminuti = orariouscita.getMinutes();
        }
        if ( (monteminuti < orariouscita.getMinutes()) && (monteore <= orariouscita.getHours()) )
            monteminuti = orariouscita.getMinutes();

        OraExitText.setText(format("%02d:%02d", monteore, monteminuti));
    }
}

