package com.application.gameoflife;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.bitvale.switcher.Switcher;
import com.bitvale.switcher.SwitcherX;

import me.shaohui.bottomdialog.BaseBottomDialog;

// 设置菜单
public class MyBottomDialog extends BaseBottomDialog {
    private LifeEvolution lifeEvolution;
    private DrawGame drawGame;
    public MyBottomDialog(LifeEvolution lifeEvolution,DrawGame drawGame){
        this.lifeEvolution=lifeEvolution;
        this.drawGame=drawGame;
    }
    @Override
    public int getLayoutRes() {
        return R.layout.bottom_dialog;
    }

    @Override
    public void bindView(View v) {
        final SwitcherX switcherX=v.findViewById(R.id.switch_pause);
        final Button btnNextOne=v.findViewById(R.id.one_next);
        final Button btnRestart=v.findViewById(R.id.restart);
        // 从存储文件中获取自动运行按钮是否打开
        final SharedPreferences sharedPreferences=getActivity().getSharedPreferences("setting_data", Activity.MODE_PRIVATE);
        boolean runMode=sharedPreferences.getBoolean("auto_run",true);
        switcherX.setChecked(runMode,true);
        switcherX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switcherX.setChecked(!switcherX.isChecked(),true);
                sharedPreferences.edit().putBoolean("auto_run",switcherX.isChecked()).apply();
                if(switcherX.isChecked()) lifeEvolution.setEvolutionFlag(true);
                else lifeEvolution.setEvolutionFlag(false);
            }
        });
        btnNextOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lifeEvolution.OneEvolution();
            }
        });
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawGame.setLife(drawGame.setStart());
                drawGame.postInvalidate();
            }
        });
    }
}
