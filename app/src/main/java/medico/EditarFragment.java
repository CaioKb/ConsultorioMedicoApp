package medico;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import application.R;
import database.DataBaseManager;

public class EditarFragment extends Fragment {

    EditText etNome;
    EditText etCRM;
    EditText etCelular;
    EditText etTelefoneFixo;
    DataBaseManager dataBase;
    Medico medico;

    public EditarFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.medico_fragment_editar, container, false);
        Bundle b = getArguments();

        int idMedico = b.getInt("id");
        dataBase = new DataBaseManager(getActivity());
        medico = dataBase.readMedico(idMedico);

        etNome = v.findViewById(R.id.editTextNomeMedico);
        etCRM = v.findViewById(R.id.editTextCrmMedico);
        etCelular = v.findViewById(R.id.editTextCelularMedico);
        etTelefoneFixo = v.findViewById(R.id.editTextFixoMedico);

        etNome.setText(medico.getNome());
        etCRM.setText(medico.getCrm());
        etCelular.setText(medico.getCelular());
        etTelefoneFixo.setText(medico.getTelefoneFixo());

        Button buttonEditar = v.findViewById(R.id.buttonEditarMedico);
        Button buttonExcluir = v.findViewById(R.id.buttonExcluirMedico);

        buttonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editar(idMedico);
            }
        });

        buttonExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { excluir(idMedico); }
        });

        return v;
    }

    private void editar (int id) {
        if (etNome.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o nome!", Toast.LENGTH_LONG).show();
        } else if (etCRM.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o CRM!", Toast.LENGTH_LONG).show();
        } else if (etCelular.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o celular!", Toast.LENGTH_LONG).show();
        } else if (etTelefoneFixo.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o telefone fixo!", Toast.LENGTH_LONG).show();
        } else {
            medico = new Medico();
            medico.setId(id);
            medico.setNome(etNome.getText().toString());
            medico.setCrm(etCRM.getText().toString());
            medico.setCelular(etCelular.getText().toString());
            medico.setTelefoneFixo(etTelefoneFixo.getText().toString());
            dataBase.updateMedico(medico);
            Toast.makeText(getActivity(), "Médico editado!", Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMedico,
                    new ListarFragment()).commit();
        }
    }

    private void excluir (int id) {
        medico = new Medico();
        medico.setId(id);
        dataBase.deleteMedico(medico);
        Toast.makeText(getActivity(), "Médico excluído!", Toast.LENGTH_LONG).show();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMedico, new ListarFragment()).commit();
    }
}
