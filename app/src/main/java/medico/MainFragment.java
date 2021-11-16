package medico;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;

import application.R;

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
        View v = inflater.inflate(R.layout.medico_fragment_main, container, false);

        Button btnAdicionar = v.findViewById(R.id.buttonAdicionarMedico);
        Button btnListar = v.findViewById(R.id.buttonListarMedico);

        btnAdicionar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMedico,
                        new AdicionarFragment()).commit();
            }
        });

        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMedico,
                        new ListarFragment()).commit();
            }
        });

        return v;
    }
}
