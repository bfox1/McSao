package io.github.bfox1.SaoAutomater;

/**
 * Created by bfox1 on 12/30/2017.
 */
public class SaoAutomater
{

    public static void main(String args[])
    {

    }

    public String simpleBlockJson()
    {
        return "" +
                "{\n" +
                "  \"parent\":\"block/cube_all\",\n" +
                "  \"textures\":\n" +
                "  {\n" +
                "    \"all\":\"sao:blocks/block_all\"\n" +
                "  }\n" +
                "}";
    }

    public String complexBlockJson()
    {
        return "{\n" +
                "  \"parent\":\"block/cube_all\",\n" +
                "  \"textures\":\n" +
                "  {\n" +
                "    \"down\":\"sao:blocks/block_down\",\n" +
                "    \"up\":\"sao:blocks/block_up\",\n" +
                "    \"north\":\"sao:blocks/block_north\",\n" +
                "    \"south\":\"sao:blocks/block_south\",\n" +
                "    \"east\":\"sao:blocks/block_east\",\n" +
                "    \"west\":\"sao:blocks/block_west\"\n" +
                "  }\n" +
                "}";
    }

    public String simpleItemJson()
    {
        return "{\n" +
                "  \"parent\":\"sao:block/item_all\",\n" +
                "  \"display\": {\n" +
                "    \"thirdperson\": {\n" +
                "      \"rotation\": [ 10, -45, 170 ],\n" +
                "      \"translation\": [ 0, 1.5, -2.75 ],\n" +
                "      \"scale\": [ 0.375, 0.375, 0.375 ]\n" +
                "    }\n" +
                "  }\n" +
                "}";
    }

    public String complexResizedItemJson()
    {
        return "\n" +
                "{\n" +
                "  \"parent\": \"builtin/generated\",\n" +
                "  \"textures\": {\n" +
                "    \"layer0\": \"sao:items/item_all\"\n" +
                "  },\n" +
                "  \"display\": {\n" +
                "    \"thirdperson\": {\n" +
                "      \"rotation\": [ -90, 0, 0 ],\n" +
                "      \"translation\": [ 0, 1, -3 ],\n" +
                "      \"scale\": [ 0.55, 0.55, 0.55 ]\n" +
                "    },\n" +
                "    \"firstperson\": {\n" +
                "      \"rotation\": [ 0, -135, 25 ],\n" +
                "      \"translation\": [ 0, 4, 2 ],\n" +
                "      \"scale\": [ 1.7, 1.7, 1.7 ]\n" +
                "    }\n" +
                "  }\n" +
                "}\n";
    }

    public String simpleBlockStateJson()
    {
        return "";
    }

}
