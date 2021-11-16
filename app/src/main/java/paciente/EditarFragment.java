package paciente;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.fragment.app.Fragment;
import application.R;
import database.DataBaseManager;
import medico.Medico;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class EditarFragment extends Fragment {

    public EditarFragment () { }

    EditText etNome;
    EditText etCelular;
    EditText etTelefoneFixo;
    DataBaseManager dataBase;
    Paciente paciente;
    Spinner spGrupoSanguineo;
    List<String> tiposSanguineos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.paciente_fragment_editar, container, false);
        Bundle b = getArguments();

        int idPaciente = b.getInt("id");
        dataBase = new DataBaseManager(getActivity());
        paciente = dataBase.readPaciente(idPaciente);

        etNome = v.findViewById(R.id.editTextNomePaciente);
        spGrupoSanguineo = v.findViewById(R.id.editSpinnerGrupoSanguineo);
        etCelular = v.findViewById(R.id.editTextCelularPaciente);
        etTelefoneFixo = v.findViewById(R.id.editTextFixoPaciente);

        tiposSanguineos = Arrays.asList(getResources().getStringArray(R.array.tiposSanguineosArray));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, tiposSanguineos);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGrupoSanguineo.setAdapter(adapter);

        etNome.setText(paciente.getNome());
        spGrupoSanguineo.setSelection(tiposSanguineos.indexOf(paciente.getGrupoSanguineo()));
        etCelular.setText(paciente.getCelular());
        etTelefoneFixo.setText(paciente.getTelefoneFixo());


        Button buttonEditarPaciente = v.findViewById(R.id.buttonEditarPaciente);
        Button buttonExcluirPaciente = v.findViewById(R.id.buttonExcluirPaciente);

        buttonEditarPaciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editar(idPaciente);
            }
        });

        buttonExcluirPaciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excluir(idPaciente);
            }
        });

        return v;

    }

    private void editar (int id) {
        if (etNome.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o nome!", Toast.LENGTH_LONG).show();
        } else if (etCelular.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o celular!", Toast.LENGTH_LONG).show();
        } else if (etTelefoneFixo.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o telefone fixo!", Toast.LENGTH_LONG).show();
        } else {
            paciente = new Paciente();
            paciente.setId(id);
            paciente.setNome(etNome.getText().toString());
            String grupoSanquineo = spGrupoSanguineo.getSelectedItem().toString();
            paciente.setGrupoSanguineo(grupoSanquineo);
            paciente.setCelular(etCelular.getText().toString());
            paciente.setTelefoneFixo(etTelefoneFixo.getText().toString());
            dataBase.updatePaciente(paciente);
            Toast.makeText(getActivity(), "Paciente editado!", Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framePaciente,
                    new ListarFragment()).commit();
        }
    }
    private void excluir (int id) {
        paciente = new Paciente();
        paciente.setId(id);
        dataBase.deletePaciente(paciente);
        Toast.makeText(getActivity(), "Paciente excluído!", Toast.LENGTH_LONG).show();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framePaciente, new ListarFragment()).commit();
    }
}
