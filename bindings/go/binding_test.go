package tree_sitter_smelly_code_detector_test

import (
	"testing"

	tree_sitter "github.com/tree-sitter/go-tree-sitter"
	tree_sitter_smelly_code_detector "github.com/tree-sitter/tree-sitter-smelly_code_detector/bindings/go"
)

func TestCanLoadGrammar(t *testing.T) {
	language := tree_sitter.NewLanguage(tree_sitter_smelly_code_detector.Language())
	if language == nil {
		t.Errorf("Error loading SmellyCodeDetector grammar")
	}
}
