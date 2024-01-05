package server.visitor;


import server.core.MusicStreamManager;

public class ServerStatisticsVisitor implements StatisticsVisitor {
    private int numberOfClients;
    private int numberOfSongsPlayed;
    private int songsInPlaylist;
     private int allClientJoinsAmount;

    @Override
    public void visit(MusicStreamManager manager) {
        numberOfClients = manager.getClients().size();
        numberOfSongsPlayed = manager.getPlayedSongsAmount();
        songsInPlaylist = manager.getSongs().size();
        allClientJoinsAmount = manager.getAllClientJoinsAmount();
    }

    public String getStringStatistics() {
        return String.format("Number of clients: %d, number of joins: %d, Song played: %d, Songs in playlist: %d",
                numberOfClients, allClientJoinsAmount , numberOfSongsPlayed, songsInPlaylist);
    }
}
