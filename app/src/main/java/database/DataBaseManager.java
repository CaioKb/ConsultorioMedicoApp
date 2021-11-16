package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import application.R;
import consulta.Consulta;
import medico.Medico;
import paciente.Paciente;

import java.util.ArrayList;

public class DataBaseManager extends SQLiteOpenHelper {

    private static final String LOG = "DatabaseHelper";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "consultorio";
    private static final String TABLE_CONSULTA = "consulta";
    private static final String TABLE_MEDICO = "medico";
    private static final String TABLE_PACIENTE = "paciente";

    private static final String SQL_CREATE_CONSULTA = "CREATE TABLE " + TABLE_CONSULTA
            + "("
            + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "idMedico INTEGER, "
            + "idPaciente INTEGER, "
            + "dataHoraInicio VARCHAR(15), "
            + "dataHoraFim VARCHAR(14), "
            + "observacao VARCHAR(100), "
            + "CONSTRAINT fk_consulta_medico FOREIGN KEY (idMedico) REFERENCES medico(id), "
            + "CONSTRAINT fk_consulta_paciente FOREIGN KEY (idPaciente) REFERENCES paciente(id)"
            + ");";

    private static final String SQL_CREATE_MEDICO = "CREATE TABLE " + TABLE_MEDICO
            + "("
            + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "nome VARCHAR(100), "
            + "crm VARCHAR(9), "
            + "celular VARCHAR(15), "
            + "telefoneFixo VARCHAR(14)"
            + ");";

    private static final String SQL_CREATE_PACIENTE = "CREATE TABLE " + TABLE_PACIENTE
            + "("
            + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "nome VARCHAR(100), "
            + "grupoSanguineo VARCHAR(9), "
            + "celular VARCHAR(15), "
            + "telefoneFixo VARCHAR(14)"
            + ");";

    private static final String SQL_DELETE_CONSULTA = "DROP TABLE IF EXISTS " + TABLE_CONSULTA;
    private static final String SQL_DELETE_MEDICO = "DROP TABLE IF EXISTS " + TABLE_MEDICO;
    private static final String SQL_DELETE_PACIENTE = "DROP TABLE IF EXISTS " + TABLE_PACIENTE;

    public DataBaseManager (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_CONSULTA);
        db.execSQL(SQL_CREATE_MEDICO);
        db.execSQL(SQL_CREATE_PACIENTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_CREATE_CONSULTA);
        db.execSQL(SQL_CREATE_MEDICO);
        db.execSQL(SQL_CREATE_PACIENTE);
        onCreate(db);
    }

    public long createConsulta(Consulta consulta) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idMedico", consulta.getIdMedico());
        values.put("idPaciente", consulta.getIdPaciente());
        values.put("dataHoraInicio", consulta.getDataHoraInicio());
        values.put("dataHoraFim", consulta.getDataHoraFim());
        values.put("observacao", consulta.getObservacao());
        long id = db.insert(TABLE_CONSULTA, null, values);
        db.close();
        return id;
    }

    public Consulta readConsulta(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"_id", "idMedico", "idPaciente", "dataHoraInicio", "dataHoraFim", "observacao"};
        String[] args = {String.valueOf(id)};

        Cursor data = db.query(TABLE_CONSULTA, columns, "_id = ?", args, null,
                null, null);
        data.moveToFirst();
        Consulta c = new Consulta();
        c.setId(data.getInt(0));
        c.setIdMedico(data.getInt(1));
        c.setIdPaciente(data.getInt(2));
        c.setDataHoraInicio(data.getString(3));
        c.setDataHoraFim(data.getString(4));
        c.setObservacao(data.getString(5));
        data.close();
        db.close();

        return c;
    }

    public long updateConsulta(Consulta consulta) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idMedico", consulta.getIdMedico());
        values.put("idPaciente", consulta.getIdPaciente());
        values.put("dataHoraInicio", consulta.getDataHoraInicio());
        values.put("dataHoraFim", consulta.getDataHoraFim());
        values.put("observacao", consulta.getObservacao());
        long rows = db.update(TABLE_CONSULTA, values, "_id = ?", new String[]{String.valueOf(consulta.getId())});
        db.close();
        return rows;
    }

    public long deleteConsulta(Consulta consulta) {
        SQLiteDatabase db = this.getWritableDatabase();
        long rows = db.delete(TABLE_CONSULTA, "_id = ?", new String[]{String.valueOf(consulta.getId())});
        db.close();
        return rows;
    }

    public void readAllConsulta (Context context, ListView lv) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"_id", "idMedico", "idPaciente", "dataHoraInicio", "dataHoraFim", "observacao"};
        Cursor data = db.query(TABLE_CONSULTA, columns, null, null, null,
                null, null);
        int[] to = {R.id.textViewIdListarConsulta, R.id.textViewNomeMedicoListarConsulta, R.id.textViewNomePacienteListarConsulta,
                R.id.textViewDataHoraInicioListarConsulta, R.id.textViewDataHoraFimListarConsulta,
        R.id.textViewObservacaoListarConsulta};
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(context,
                R.layout.consulta_item_list_view, data, columns, to, 0);
        lv.setAdapter(simpleCursorAdapter);
        db.close();
    }

    //-------------------------------------------------------------------------------------

    public long createMedico(Medico medico) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nome", medico.getNome());
        values.put("crm", medico.getCrm());
        values.put("celular", medico.getCelular());
        values.put("telefoneFixo", medico.getTelefoneFixo());
        long id = db.insert(TABLE_MEDICO, null, values);
        db.close();
        return id;
    }

    public Medico readMedico(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"_id", "nome", "crm", "celular", "telefoneFixo"};
        String[] args = {String.valueOf(id)};

        Cursor data = db.query(TABLE_MEDICO, columns, "_id = ?", args, null,
                null, null);
        data.moveToFirst();
        Medico m = new Medico();
        m.setId(data.getInt(0));
        m.setNome(data.getString(1));
        m.setCrm(data.getString(2));
        m.setCelular(data.getString(3));
        m.setTelefoneFixo(data.getString(4));
        data.close();
        db.close();

        return m;
    }

    public long updateMedico(Medico medico) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nome", medico.getNome());
        values.put("crm", medico.getCrm());
        values.put("celular", medico.getCelular());
        values.put("telefoneFixo", medico.getTelefoneFixo());
        long rows = db.update(TABLE_MEDICO, values, "_id = ?", new String[]{String.valueOf(medico.getId())});
        db.close();
        return rows;
    }

    public long deleteMedico(Medico medico) {
        SQLiteDatabase db = this.getWritableDatabase();
        long rows = db.delete(TABLE_MEDICO, "_id = ?", new String[]{String.valueOf(medico.getId())});
        db.close();
        return rows;
    }

    public void readAllMedico (Context context, ListView lv) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"_id", "nome", "crm", "celular", "telefoneFixo"};
        Cursor data = db.query(TABLE_MEDICO, columns, null, null, null,
                null, "nome");
        int[] to = {R.id.textViewIdListarMedico, R.id.textViewNomeListarMedico,
                R.id.textViewCrmListarMedico, R.id.textViewCelularListarMedico,
        R.id.textViewFixoListarMedico};
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(context,
                R.layout.medico_item_list_view, data, columns, to, 0);
        lv.setAdapter(simpleCursorAdapter);
        db.close();
    }

    public void readNameAllMedico(ArrayList<Integer> listaMedicoId, ArrayList<String> listaMedicoNome) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"_id", "nome", "crm", "celular", "telefoneFixo"};
        Cursor data = db.query(TABLE_MEDICO, columns, null, null, null,
                null, "nome");
        while(data.moveToNext()) {
            int idColumnIndex = data.getColumnIndex("_id");
            listaMedicoId.add(Integer.parseInt(data.getString(idColumnIndex)));
            int nameColumnIndex = data.getColumnIndex("nome");
            listaMedicoNome.add(data.getString(nameColumnIndex));
        }
        db.close();
    }

    //-------------------------------------------------------------------------------------

    public long createPaciente(Paciente paciente) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nome", paciente.getNome());
        values.put("grupoSanguineo", paciente.getGrupoSanguineo());
        values.put("celular", paciente.getCelular());
        values.put("telefoneFixo", paciente.getTelefoneFixo());
        long id = db.insert(TABLE_PACIENTE, null, values);
        db.close();
        return id;
    }

    public Paciente readPaciente(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"_id", "nome", "grupoSanguineo", "celular", "telefoneFixo"};
        String[] args = {String.valueOf(id)};
        Cursor data = db.query(TABLE_PACIENTE, columns, "_id = ?", args, null,
                null, null);
        data.moveToFirst();
        Paciente p = new Paciente();
        p.setId(data.getInt(0));
        p.setNome(data.getString(1));
        p.setGrupoSanguineo(data.getString(2));
        p.setCelular(data.getString(3));
        p.setTelefoneFixo(data.getString(4));
        data.close();
        db.close();

        return p;
    }

    public long updatePaciente(Paciente paciente) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nome", paciente.getNome());
        values.put("grupoSanguineo", paciente.getGrupoSanguineo());
        values.put("celular", paciente.getCelular());
        values.put("telefoneFixo", paciente.getTelefoneFixo());
        long rows = db.update(TABLE_PACIENTE, values, "_id = ?", new String[]{String.valueOf(paciente.getId())});
        db.close();

        return rows;
    }

    public long deletePaciente(Paciente paciente) {
        SQLiteDatabase db = this.getWritableDatabase();
        long rows = db.delete(TABLE_PACIENTE, "_id = ?", new String[]{String.valueOf(paciente.getId())});
        db.close();

        return rows;
    }


    public void readAllPaciente (Context context, ListView lv) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"_id", "nome", "grupoSanguineo", "celular", "telefoneFixo"};
        Cursor data = db.query(TABLE_PACIENTE, columns, null, null, null,
                null, "nome");
        int[] to = {R.id.textViewIdListarPaciente, R.id.textViewNomeListarPaciente,
                R.id.textViewGrupoSanguineoListarPaciente, R.id.textViewCelularListarPaciente,
        R.id.textViewFixoListarPaciente};
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(context,
                R.layout.paciente_item_list_view, data, columns, to, 0);
        lv.setAdapter(simpleCursorAdapter);
        db.close();
    }

    public void readNameAllPaciente(ArrayList<Integer> listaPacienteId, ArrayList<String> listaPacienteNome) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"_id", "nome", "grupoSanguineo", "celular", "telefoneFixo"};
        Cursor data = db.query(TABLE_PACIENTE, columns, null, null, null,
                null, "nome");
        while(data.moveToNext()) {
            int idColumnIndex = data.getColumnIndex("_id");
            listaPacienteId.add(Integer.parseInt(data.getString(idColumnIndex)));
            int nameColumnIndex = data.getColumnIndex("nome");
            listaPacienteNome.add(data.getString(nameColumnIndex));
        }
        db.close();
    }

    //-------------------------------------------------------------------------------------

}
