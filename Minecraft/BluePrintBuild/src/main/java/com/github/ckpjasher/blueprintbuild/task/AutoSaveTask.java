package com.github.ckpjasher.blueprintbuild.task;

import com.github.ckpjasher.blueprintbuild.configuration.subconfig.DataYaml;

public class AutoSaveTask implements Runnable {
    @Override
    public void run() {
        DataYaml.getInstance().saveMaterialLibraries();
    }
}
