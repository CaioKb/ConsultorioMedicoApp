package consulta;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.fragment.app.Fragment;
import application.R;
import database.DataBaseManager;

import java.util.*;

import static consulta.MainFragment.ValidarData;
import static consulta.MainFragment.ValidarDataFim;

public class AdicionarFragment extends Fragment {

    EditText etDataHoraInicio;
    EditText etDataHoraFim;
    EditText etObservacao;
    Spinner spMedico;
    Spinner spPaciente;

    ArrayList<Integer> listaIdMedico;
    ArrayList<Integer> listaIdPaciente;
    ArrayList<String> listaNomeMedico;
    ArrayList<String> listaNomePaciente;

    DataBaseManager dataBaseManager;

    public AdicionarFragment () { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstaceState) {

        View v = inflater.inflate(R.layout.consulta_fragment_adicionar, container, false);

        spMedico = v.findViewById(R.id.editSpinnerNomeMedicoConsulta);
        spPaciente = v.findViewById(R.id.editSpinnerNomePacienteConsulta);
        etDataHoraInicio = v.findViewById(R.id.editTextDataHoraInicioConsulta);
        etDataHoraFim = v.findViewById(R.id.editTextDataHoraFimConsulta);
        etObservacao = v.findViewById(R.id.editTextObservacaoConsulta);

        dataBaseManager = new DataBaseManager(getActivity());

        listaIdMedico = new ArrayList<>();
        listaIdPaciente = new ArrayList<>();

        listaNomeMedico = new ArrayList<>();
        listaNomePaciente = new ArrayList<>();

        dataBaseManager.readNameAllMedico(listaIdMedico, listaNomeMedico);
        dataBaseManager.readNameAllPaciente(listaIdPaciente, listaNomePaciente);

        ArrayAdapter<String> spMedicoArrayAdapter = new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_spinner_item, listaNomeMedico);
        spMedico.setAdapter(spMedicoArrayAdapter);

        ArrayAdapter<String> spPacienteArrayAdapter = new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_spinner_item, listaNomePaciente);
        spPaciente.setAdapter(spPacienteArrayAdapter);

        Button buttonAdicionarConsulta = v.findViewById(R.id.buttonSalvarNovaConsulta);

        buttonAdicionarConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionar();
            }
        });

        return v;
    }

    private void adicionar () {
        String dataInicio = etDataHoraInicio.getText().toString();
        String dataFim = etDataHoraFim.getText().toString();
        String nomeMedico = spMedico.getSelectedItem() != null ? spMedico.getSelectedItem().toString() : "";
        String nomePaciente = spPaciente.getSelectedItem() != null ? spPaciente.getSelectedItem().toString() : "";
        if (dataInicio.equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe a data do início da consulta!", Toast.LENGTH_LONG).show();
        } else if(!ValidarData(dataInicio)) {
            Toast.makeText(getActivity(), "Data de inicio da consulta inválida.", Toast.LENGTH_LONG).show();
        } else if (dataFim.equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe a data do fim da consulta!", Toast.LENGTH_LONG).show();
        } else if(!ValidarData(dataFim) || !ValidarDataFim(dataInicio, dataFim)) {
            Toast.makeText(getActivity(), "Data de fim da consulta inválida.", Toast.LENGTH_LONG).show();
        } else if (etObservacao.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, insira uma observação!", Toast.LENGTH_LONG).show();
        } else {
            DataBaseManager dataBase = new DataBaseManager(getActivity());
            Consulta c = new Consulta();
            c.setId(0);
            c.setIdMedico(listaIdMedico.get(listaNomeMedico.indexOf(nomeMedico)));
            c.setIdMedico(listaIdPaciente.get(listaNomePaciente.indexOf(nomePaciente)));
            c.setDataHoraInicio(etDataHoraInicio.getText().toString());
            c.setDataHoraFim(etDataHoraFim.getText().toString());
            c.setObservacao(etObservacao.getText().toString());
            dataBaseManager.createConsulta(c);
            Toast.makeText(getActivity(), "Consulta criada!", Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameConsulta,
                    new ListarFragment()).commit();
        }
    }
}
