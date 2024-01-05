package server.visitor;

import server.core.MusicStreamManager;

public interface StatisticsVisitor {
    void visit(MusicStreamManager manager);
}
