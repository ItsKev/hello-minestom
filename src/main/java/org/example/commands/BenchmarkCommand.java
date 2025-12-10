package org.example.commands;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.arguments.number.ArgumentInteger;
import net.minestom.server.monitoring.BenchmarkManager;

import java.time.Duration;
import java.util.Locale;

public class BenchmarkCommand extends Command {
    public BenchmarkCommand() {
        super("benchmark", "bench");

        var actionArg = ArgumentType.Word("action").from("enable", "disable", "results");
        ArgumentInteger durationArg = ArgumentType.Integer("seconds");

        BenchmarkManager benchmarkManager = MinecraftServer.getBenchmarkManager();

        addSyntax((sender, context) -> {
            String action = context.get(actionArg);
            if (!"enable".equals(action)) {
                sender.sendMessage("Only enable accepts seconds. Usage: /benchmark enable <seconds>");
                return;
            }
            int seconds = context.get(durationArg);
            try {
                benchmarkManager.enable(Duration.ofSeconds(seconds));
                sender.sendMessage(String.format(Locale.US, "Benchmarking enabled for %ds.", seconds));
            } catch (Exception exception) {
                sender.sendMessage("Could not enable benchmarking: " + exception.getMessage());
            }
        }, actionArg, durationArg);

        addSyntax((sender, context) -> {
            String action = context.get(actionArg);
            switch (action) {
                case "enable" -> {
                    int seconds = 10;
                    try {
                        benchmarkManager.enable(Duration.ofSeconds(seconds));
                        sender.sendMessage(String.format(Locale.US, "Benchmarking enabled for %ds (default).", seconds));
                    } catch (Exception exception) {
                        sender.sendMessage("Could not enable benchmarking: " + exception.getMessage());
                    }
                }
                case "disable" -> {
                    benchmarkManager.disable();
                    sender.sendMessage("Benchmarking disabled.");
                }
                case "results" -> {
                    sender.sendMessage(benchmarkManager.getCpuMonitoringMessage());
                    double usedMb = benchmarkManager.getUsedMemory() / 1024D / 1024D;
                    sender.sendMessage(String.format(Locale.US, "Used memory: %.2f MB", usedMb));
                }
                default -> sender.sendMessage("Unknown action.");
            }
        }, actionArg);
    }
}
