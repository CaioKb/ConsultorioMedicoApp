package application;

import android.os.Bundle;
import android.view.*;
import androidx.fragment.app.Fragment;

public class MenuFragment extends Fragment {
    public MenuFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_medico:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new medico.MainFragment()).commit();
                break;
            case R.id.menu_paciente:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new paciente.MainFragment()).commit();
                break;
            case R.id.menu_consultas:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new consulta.MainFragment()).commit();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

}
