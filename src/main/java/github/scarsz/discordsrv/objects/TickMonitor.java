/*
 * DiscordSRV - A Minecraft to Discord and back link plugin
 * Copyright (C) 2016-2020 Austin "Scarsz" Shapiro
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package github.scarsz.discordsrv.objects;

import github.scarsz.discordsrv.DiscordSRV;

import java.text.DecimalFormat;
import java.util.concurrent.atomic.AtomicReference;

public class TickMonitor implements Runnable {

    public static final int REQUIRED_SAMPLES = 100;
    private final long[] TICKS = new long[REQUIRED_SAMPLES];
    private int TICK_COUNT = 0;

    private static final AtomicReference<TickMonitor> INSTANCE = new AtomicReference<>();

    private TickMonitor() {
    }

    public static TickMonitor getInstance() {
        return INSTANCE.updateAndGet(oldTickMonitor -> oldTickMonitor != null ? oldTickMonitor : new TickMonitor());
    }

    public String getTPSString() {
        try {
            if (TICK_COUNT >= REQUIRED_SAMPLES) {
                int oldestTick = (TICK_COUNT + 1) % TICKS.length;
                double tps = REQUIRED_SAMPLES / ((System.currentTimeMillis() - TICKS[oldestTick]) / 1000.0D);
                tps = Math.min(tps, 20.0D);
                DecimalFormat df = new DecimalFormat();
                df.setMaximumFractionDigits(2);
                return df.format(tps);
            } else {
                return "Not enough samples (Req. " + REQUIRED_SAMPLES + ")";
            }
        } catch (Exception e) {
            DiscordSRV.warning("Error computing TPS: " + e.getMessage());
            return "Error occurred computing TPS";
        }
    }

    public void run() {
        TICKS[(TICK_COUNT % TICKS.length)] = System.currentTimeMillis();
        TICK_COUNT += 1;
        if (TICK_COUNT == REQUIRED_SAMPLES) {
            TICK_COUNT = 0;
        }
    }
}
