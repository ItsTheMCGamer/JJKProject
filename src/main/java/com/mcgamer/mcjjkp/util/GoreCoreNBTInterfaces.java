package com.mcgamer.mcjjkp.util;

import net.minecraft.commands.arguments.NbtTagArgument;

public class GoreCoreNBTInterfaces {

    public interface ReadableWritable {
        void readFromNBT(NbtTagArgument nbt);

        void writeToNBT(NbtTagArgument nbt);
    }

    public interface CreateFromNBT<T> {
        /**
         * Called to get a new <code>T</code> from the NBTTagCompound.
         *
         * @param nbt
         *            The NBTTagCompound to get it from
         * @param methodsExtraData
         *            Method-specific extra data provided by the method, the
         *            details should be provided in a javadoc
         * @param extraData
         *            Extra data provided by parameters in the method; specific
         *            per implementation of CreateFromNBT
         * @return A new instance of <code>T</code>
         */
        T create(NbtTagArgument nbt, Object[] methodsExtraData, Object[] extraData);
    }

    public interface WriteToNBT<T> {
        /**
         * Called to write the object of type <code>T</code> to the
         * NBTTagCompound.
         *
         * @param nbt
         *            The NBTTagCompound to write the data to
         * @param object
         *            The object to write to NBT
         * @param methodsExtraData
         *            Method-specific extra data provided by the method, the
         *            details should be provided in a javadoc
         * @param extraData
         *            Extra data provided by parameters in the method; specific
         *            per implementation of WriteToNBT
         */
        void write(NbtTagArgument nbt, T object, Object[] methodsExtraData, Object[] extraData);
    }

    public interface MapUser<K, V> {

        K createK(NbtTagArgument nbt, Object[] constructArgsK);

        V createV(NbtTagArgument nbt, K key, Object[] constructArgsV);

        /**
         * Write the given key object to NBT.
         *
         * @param nbt
         *            The NBTTagCompound to store the key's data in
         */
        void writeK(NbtTagArgument nbt, K obj);

        /**
         * Write the given value object to NBT.
         *
         * @param nbt
         *            The NBTTagCompound to store the value's data in
         */
        void writeV(NbtTagArgument nbt, V obj);

    }

}