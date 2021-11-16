package consulta;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;

import application.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class MainFragment extends Fragment {

    public MainFragment () { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.consulta_fragment_main, container, false);

        Button btnAdicionar = v.findViewById(R.id.buttonAdicionarConsulta);
        Button btnListar = v.findViewById(R.id.buttonListarConsulta);

        btnAdicionar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameConsulta,
                        new AdicionarFragment()).commit();
            }
        });

        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameConsulta,
                        new ListarFragment()).commit();
            }
        });

        return v;
    }
    protected static boolean ValidarData (String data) {

        Calendar calendar = Calendar.getInstance();

        try {
            calendar.setTime(Objects.requireNonNull(new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH).parse(data)));
        }
        catch(ParseException e) {
            e.printStackTrace();
        }

        return ValidarDiaDaSemana(calendar) && ValidarHorarioConsulta(calendar);
    }

    protected static boolean ValidarDiaDaSemana(Calendar calendar) {
        return calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY
                && calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY;
    }

    protected static boolean ValidarHorarioConsulta (Calendar calendar) {
        Calendar horarioAberturaManha = Calendar.getInstance();
        horarioAberturaManha.setTime(calendar.getTime());
        horarioAberturaManha.set(Calendar.HOUR_OF_DAY, 8);
        horarioAberturaManha.set(Calendar.MINUTE, 0);


        Calendar horarioFechamentoManha = Calendar.getInstance();
        horarioFechamentoManha.setTime(calendar.getTime());
        horarioFechamentoManha.set(Calendar.HOUR_OF_DAY, 12);
        horarioFechamentoManha.set(Calendar.MINUTE, 0);

        Calendar horarioAberturaTarde = Calendar.getInstance();
        horarioAberturaTarde.setTime(calendar.getTime());
        horarioAberturaTarde.set(Calendar.HOUR_OF_DAY, 13);
        horarioAberturaTarde.set(Calendar.MINUTE, 30);

        Calendar horarioFechamentoTarde = Calendar.getInstance();
        horarioFechamentoTarde.setTime(calendar.getTime());
        horarioFechamentoTarde.set(Calendar.HOUR_OF_DAY, 17);
        horarioFechamentoTarde.set(Calendar.MINUTE, 30);

        boolean ehHorarioDaManha = calendar.after(horarioAberturaManha) && calendar.before(horarioFechamentoManha);
        boolean ehHorarioDaTarde = calendar.after(horarioAberturaTarde) && calendar.before(horarioFechamentoTarde);

        return ehHorarioDaManha || ehHorarioDaTarde;
    }

    protected static boolean ValidarDataFim (String inicio, String fim) {
        Calendar dataInicio = Calendar.getInstance();
        Calendar dataFim = Calendar.getInstance();

        try {
            dataInicio.setTime(Objects.requireNonNull(new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH).parse(inicio)));
            dataFim.setTime(Objects.requireNonNull(new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH).parse(fim)));
        }
        catch(ParseException e) {
            e.printStackTrace();
        }

        return dataInicio.before(dataFim);
    }
}

