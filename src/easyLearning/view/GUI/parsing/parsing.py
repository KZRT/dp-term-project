import re


# name_lists
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


# createCLusterer
# createClustererEvaluation
# createDistanceMEasure
def read_file(name):
    file_path = name
    formatted_codes = ""

    # 파일 열기
    with open(file_path, "r") as file:
        # 파일의 각 줄에 대해 반복
        for line in file:
            # 각 줄의 텍스트 파싱 또는 처리
            # 예를 들어, 각 줄의 단어들을 리스트로 저장하는 경우:
            words = export_format(line.strip())
            if words is not None and words != "Set distance measure first!" and words != "Set radius first!" and words != "Set data first!":
                formatted_codes += words + "\n"

        return formatted_codes


def export_format(input_string):
    # 정규표현식 패턴
    pattern = r'"(.*?)"'

    # 정규표현식을 사용하여 매칭된 부분을 추출
    match = re.search(pattern, input_string)

    # 매칭된 부분이 있다면 출력, 없다면 에러 메시지 출력
    if match:
        extracted_text = match.group(1)
        return extracted_text
    else:
        return


if __name__ == '__main__':
    # code = make_name_list()
    # print(code)
    print(read_file("createDistanceMeasure.txt"))
