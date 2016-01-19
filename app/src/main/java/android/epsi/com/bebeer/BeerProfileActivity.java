package android.epsi.com.bebeer;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class BeerProfileActivity extends AppCompatActivity {

    private TextView commentTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Beer beer = new Beer();

        beer.setName("Krönembourg");
        beer.setComment("This beer might not be the best beer ever, although it does the taff!");

        toolbar.setTitle(beer.getName());
        commentTv = (TextView) findViewById(R.id.content_beer_comment);

        commentTv.setText(beer.getComment());
    }


    /**
     * TODO: delete this and use real bean
     */
    private class Beer {
        private String name;
        private String comment;

        public void setName(String name) {
            this.name = name;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getName() {
            return name;
        }

        public String getComment() {
            return comment;
        }
    }
}
