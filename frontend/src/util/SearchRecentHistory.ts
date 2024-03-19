import React, {useEffect, useState} from "react";

const HISTORY_LIMIT = 5;

export const SearchRecentHistory = () => {
    const [keywords, setKeywords] = useState<string[]>(JSON.parse(localStorage.getItem("EVENTTY_RECENT_HISTORY") || "[]"));

    useEffect(() => {
        localStorage.setItem("EVENTTY_RECENT_HISTORY", JSON.stringify(keywords));
    }, [keywords]);

    const handleAddKeyword = (keyword: string) => {
        const updatedKeywords = [keyword, ...keywords.filter(item => item !== keyword)];

        if (updatedKeywords.length > HISTORY_LIMIT){
            updatedKeywords.splice(HISTORY_LIMIT);
        }

        setKeywords(updatedKeywords);
    }


    const handleDeleteKeyword = (keyword: string) => {
        setKeywords([...keywords.filter(item => item !== keyword)]);
    }

    return {keywords, handleAddKeyword, handleDeleteKeyword};
}