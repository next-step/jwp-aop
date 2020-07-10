package core.di.factory.example;

import core.annotation.Inject;
import core.annotation.Qualifier;
import core.annotation.Service;

/**
 * @author KingCjy
 */
@Service
public class BoardService {

    private BoardRepository boardRepository;

    @Inject
    public BoardService(@Qualifier("mock") BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public BoardRepository getBoardRepository() {
        return boardRepository;
    }
}
