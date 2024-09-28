import XCTest
import SwiftTreeSitter
import TreeSitterSmellyCodeDetector

final class TreeSitterSmellyCodeDetectorTests: XCTestCase {
    func testCanLoadGrammar() throws {
        let parser = Parser()
        let language = Language(language: tree_sitter_smelly_code_detector())
        XCTAssertNoThrow(try parser.setLanguage(language),
                         "Error loading SmellyCodeDetector grammar")
    }
}
