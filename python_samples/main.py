import dataclasses
import sys
from abc import ABCMeta
from dataclasses import dataclass
from typing import Protocol, cast


class DataInputStream(object):

    def __init__(self, data: bytes):
        self.data = data
        self.position = 0

    def peek(self, size: int) -> int:
        crop = self.data[self.position:self.position+size]
        return int.from_bytes(crop, byteorder="big")

    def read_int(self, size: int) -> int:
        value = self.peek(size)
        self.position += size
        return value

    def read_int8(self) -> int:
        return self.read_int(1)

    def read_int16(self) -> int:
        return self.read_int(2)

    def read_int32(self) -> int:
        return self.read_int(4)


class Serializable(Protocol):

    @classmethod
    def read(cls, stream: DataInputStream) -> "Serializable": ...


class _SerializableIntMeta(Serializable, ABCMeta):

    __size__: int

    def __getitem__(self, args) -> "SerializableInt":
        name = args[0]
        size = args[1]
        # noinspection PyTypeChecker
        return type(name, (self, ), dict(__size__=size))


class SerializableInt(int, metaclass=_SerializableIntMeta):

    @classmethod
    def read(cls, stream: DataInputStream) -> "SerializableInt":
        return cls(stream.read_int(cls.__size__))


class Int8NoMeta(int, SerializableInt):
    __size__ = 1
    __param__ = 2


Int8 = SerializableInt["Int8", 1]
Int16 = SerializableInt["Int16", 2]
Int32 = SerializableInt["Int32", 4]


class Int8Stat(int, Serializable):

    @staticmethod
    def read_static(stream: DataInputStream):
        return Int8Stat(stream.read_int(1))


@dataclass
class Struct(Serializable):

    @classmethod
    def read(cls, stream: DataInputStream) -> "Struct":
        values = {field.name: field.type.read(stream) for field in dataclasses.fields(cls)}
        return cls(**values)


class IntSerializer(object):
    pass


def serializer(cls):

    def registrator(field: dataclasses.Field):
        field.__serializer__ = cls
        return field

    return registrator


@dataclass
class CpuContext(Struct):

    # @serializer(IntSerializer)
    eax: Int32

    ebx: Int32
    ecx: Int32
    edx: Int32

    flags: Int16


types: dict[str, Serializable] = {
    "Int8": Int8,
    "Int16": Int16,
    "Int32": Int32,
    "CpuContext": CpuContext
}


def main(args):
    data = bytes.fromhex(args[1])
    stream = DataInputStream(data)

    for typename in args[2:]:
        typ: Serializable = types[typename]
        value = typ.read(stream)
        value = cast(CpuContext, value)
        print(value.eax + value.ebx)
        print(f"value: {type(value).__name__} = {value}")


if __name__ == '__main__':
    # DEADBEEFAABBCCDDDEADBEEFAABBCCDD0022 CpuContext
    main(sys.argv)
