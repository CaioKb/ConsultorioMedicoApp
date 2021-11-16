package paciente;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.fragment.app.Fragment;
import application.R;
import database.DataBaseManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdicionarFragment extends Fragment {

    EditText etNome;
    EditText etCelular;
    EditText etTelefoneFixo;
    Spinner spGrupoSanguineo;
    List<String> tiposSanguineos;

    public AdicionarFragment() { }

    @Override
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.paciente_fragment_adicionar, container, false);

        spGrupoSanguineo = v.findViewById(R.id.editSpinnerGrupoSanguineo);
        etNome = v.findViewById(R.id.editTextNomePaciente);
        etCelular = v.findViewById(R.id.editTextCelularPaciente);
        etTelefoneFixo = v.findViewById(R.id.editTextFixoPaciente);

        tiposSanguineos = Arrays.asList(getResources().getStringArray(R.array.tiposSanguineosArray));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, tiposSanguineos);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGrupoSanguineo.setAdapter(adapter);

        Button buttonSalvarPaciente = v.findViewById(R.id.buttonSalvarNovoPaciente);
        buttonSalvarPaciente.setOnClickListener(new View.OnClickListener() {
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
        } else if (etCelular.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o celular!", Toast.LENGTH_LONG).show();
        } else if (etTelefoneFixo.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o telefone fixo!", Toast.LENGTH_LONG).show();
        } else {
            DataBaseManager dataBase = new DataBaseManager(getActivity());
            Paciente paciente = new Paciente();
            paciente.setId(0);
            paciente.setNome(etNome.getText().toString());
            String grupoSanquineo = spGrupoSanguineo.getSelectedItem().toString();
            paciente.setGrupoSanguineo(grupoSanquineo);
            paciente.setCelular(etCelular.getText().toString());
            paciente.setTelefoneFixo(etTelefoneFixo.getText().toString());
            dataBase.createPaciente(paciente);
            Toast.makeText(getActivity(), "Paciente salvo!", Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framePaciente,
                    new ListarFragment()).commit();
        }
    }
}
