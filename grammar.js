/// <reference types="tree-sitter-cli/dsl" />
// @ts-check

module.exports = grammar({
  name: "smelly_code_detector",

  rules: {
    // TODO: add the actual grammar rules
    source_file: $ => "hello"
  }
});
