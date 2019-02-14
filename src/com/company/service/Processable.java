package com.company.service;

import java.io.IOException;

public interface Processable {
    void process() throws IOException, InterruptedException;
}
