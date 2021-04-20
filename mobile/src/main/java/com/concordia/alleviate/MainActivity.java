package com.concordia.alleviate;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.wearable.CapabilityClient;
import com.google.android.gms.wearable.CapabilityInfo;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final String CAPABILITY_NAME = "start_alleviate";
    private String nodeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(this::getCompatibleWearableNodes).start();

        FloatingActionButton fab = findViewById(R.id.main_fab);
        fab.setOnClickListener(v -> {
            sendMessageToWatch();
        });

        BottomNavigationView bottomNav = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNav, navController);
    }

    private void sendMessageToWatch() {
        new Thread(() -> {
            if (nodeId == null) {
                showSnackbar(false);
                return;
            }
            Task<Integer> task = Wearable
                .getMessageClient(this)
                .sendMessage(nodeId, "/alleviate/now", null);
            task.addOnSuccessListener(integer -> {
                showSnackbar(true);
            });
            task.addOnFailureListener(e -> {
                showSnackbar(false);
            });
        }).start();
    }

    private void getCompatibleWearableNodes() {
        try {
            CapabilityInfo info = Tasks.await(
                Wearable
                    .getCapabilityClient(this)
                    .getCapability(
                        CAPABILITY_NAME,
                        CapabilityClient.FILTER_REACHABLE
                    )
            );
            updateSelectedNode(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        CapabilityClient.OnCapabilityChangedListener listener = this::updateSelectedNode;
        Wearable.getCapabilityClient(this).addListener(listener, CAPABILITY_NAME);
    }

    private void updateSelectedNode(CapabilityInfo capabilityInfo) {
        Set<Node> connectedNodes = capabilityInfo.getNodes();
        for (Node node : connectedNodes) {
            if (node.isNearby()) {
                nodeId = node.getId();
                return;
            }
            nodeId = node.getId();
        }
    }

    private void showSnackbar(boolean success) {
        int color = getColor(R.color.ming);
        int message = R.string.fab_snackbar_alert;

        if (!success) {
            color = getColor(R.color.fiery_rose);
            message = R.string.fab_snackbar_error;
        }

        Snackbar snackbar = Snackbar.make(findViewById(R.id.container), message, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(color);

        View view = snackbar.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params);

        snackbar.show();
    }
}