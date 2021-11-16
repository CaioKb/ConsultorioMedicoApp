package medico;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.fragment.app.Fragment;
import application.R;

import android.widget.Toast;
import database.DataBaseManager;

public class AdicionarFragment extends Fragment {

    EditText etNome;
    EditText etCRM;
    EditText etCelular;
    EditText etTelefoneFixo;

    public AdicionarFragment() {}

    @Override
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.medico_fragment_adicionar, container, false);

        etNome = v.findViewById(R.id.editTextNomeMedico);
        etCRM = v.findViewById(R.id.editTextCRMMedico);
        etCelular = v.findViewById(R.id.editTextCelularMedico);
        etTelefoneFixo = v.findViewById(R.id.editTextFixoMedico);

        Button buttonSalvarMedico = v.findViewById(R.id.buttonSalvarNovoMedico);

        buttonSalvarMedico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionar();
            }
        });

        return v;
    }

    private void adicionar () {
        if (etNome.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o nome!", Toast.LENGTH_LONG).show();
        } else if (etCRM.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o CRM!", Toast.LENGTH_LONG).show();
        } else if (etCelular.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o celular!", Toast.LENGTH_LONG).show();
        } else if (etTelefoneFixo.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o telefone fixo!", Toast.LENGTH_LONG).show();
        } else {
            DataBaseManager dataBase= new DataBaseManager(getActivity());
            Medico m = new Medico();
            m.setId(0);
            m.setNome(etNome.getText().toString());
            m.setCrm(etCRM.getText().toString());
            m.setCelular(etCelular.getText().toString());
            m.setTelefoneFixo(etTelefoneFixo.getText().toString());
            dataBase.createMedico(m);
            Toast.makeText(getActivity(), "Médico salvo!", Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMedico,
                    new ListarFragment()).commit();
        }
    }
}
