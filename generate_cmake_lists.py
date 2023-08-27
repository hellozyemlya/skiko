import argparse
import os.path
from typing import Tuple, List
import pathlib
import fnmatch

ROOT = os.path.dirname(__file__)

arguments_mapping = {
    "win-x64-jvm": "skiko/build/tmp/compileJvmBindingsWindowsX64/common-args.txt"
}
source_mappings = {
    "win-x64-jvm": "skiko/build/tmp/compileJvmBindingsWindowsX64/source-to-output.txt"
}


def parse_includes_and_headers(_os: str) -> Tuple[List[str], List[str]]:
    include = []
    define = []
    lines = iter((pathlib.Path(ROOT) / arguments_mapping[_os]).read_text().splitlines(keepends=False))

    try:
        while True:
            cur_argument = next(lines)
            # handle gcc includes
            if cur_argument == "-I":
                include.append(next(lines))
                continue
            # handle windows includes
            if cur_argument.startswith("/I"):
                include.append(cur_argument[2:].strip("\"").replace("\\\\", "/"))
                continue
            if cur_argument.startswith("-D"):
                define.append(cur_argument[2:])
                continue
    except StopIteration:
        pass

    return include, define


def parse_sources(_os: str) -> List[str]:
    lines = (pathlib.Path(ROOT) / source_mappings[_os]).read_text().splitlines(keepends=False)

    def replace_sep(path: str) -> str:
        if "win" in _os:
            return path.replace("\\", "/")
        else:
            return path

    return list([replace_sep(line.split(";")[0]) for line in lines])


def main():
    parser = argparse.ArgumentParser(description='Generates CMakeLists.txt file for skiko native')
    parser.add_argument(
        'os',
        choices=['win-x64-jvm', 'osx-x64-jvm', 'osx-arm64-jvm'],
        help='Specify the target operating system (win-x64-jvm, osx-x64-jvm, osx-arm64-jvm)'
    )

    args = parser.parse_args()

    includes, defines = parse_includes_and_headers(args.os)
    sources = parse_sources(args.os)
    with (pathlib.Path(ROOT) / "CMakeLists.txt").open("w") as cmake:
        cmake.write("cmake_minimum_required(VERSION 3.24)\n")
        cmake.write("project(skiko)\n")
        cmake.write("set(CMAKE_CXX_STANDARD 17)\n")
        cmake.write("set(source_files\n")
        for source in sources:
            cmake.write(f"    \"{source}\"\n")
        cmake.write(")\n")
        cmake.write("add_library(skiko OBJECT ${source_files})\n")
        cmake.write("target_include_directories(skiko PRIVATE\n")
        for include in includes:
            cmake.write(f"    \"{include}\"\n")
        cmake.write(")\n")
        cmake.write("target_compile_definitions(skiko PRIVATE\n")
        for define in defines:
            cmake.write(f"    {define}\n")
        cmake.write(")\n")


if __name__ == '__main__':
    main()
