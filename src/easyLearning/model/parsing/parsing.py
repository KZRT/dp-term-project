

def make_name_list():
    file_path = "name_lists.txt"
    formatted_codes = ""

    # 파일 열기
    with open(file_path, "r") as file:
        # 파일의 각 줄에 대해 반복
        for line in file:
            # 각 줄의 텍스트 파싱 또는 처리
            # 예를 들어, 각 줄의 단어들을 리스트로 저장하는 경우:
            words = remove_file_extension(line.strip())
            format_words = format_names(words)
            formatted_codes += format_words
        return formatted_codes


# https://www.jetbrains.com/help/pycharm/에서 PyCharm 도움말 확인
def format_names(name):
    formatted_code = ""

    formatted_code += f"case \"{name}\":\n"
    formatted_code += f"    return new {name}();\n"

    return formatted_code


def remove_file_extension(file_name, extension=".java"):
    if file_name.endswith(extension):
        # 파일명이 지정된 확장자로 끝나면 확장자를 제거
        return file_name[:-len(extension)]
    else:
        # 다른 경우 그대로 반환
        return file_name

if __name__ == '__parsing__':
    code = make_name_list()
    print(code)
