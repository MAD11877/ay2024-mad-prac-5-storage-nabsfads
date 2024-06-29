
package sg.edu.np.mad.madpractical5;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DatabaseHandler dbHandler = new DatabaseHandler(this);
        List<User> userList = dbHandler.getUsers();

        UserAdapter userAdapter = new UserAdapter(userList, user -> {
            Intent intent = new Intent(ListActivity.this, MainActivity.class);
            intent.putExtra("id", user.getId());
            intent.putExtra("name", user.getName());
            intent.putExtra("description", user.getDescription());
            intent.putExtra("followed", user.getFollowed());
            startActivity(intent);
        });
        recyclerView.setAdapter(userAdapter);
    }
}
