SELECT 
    CONDITION_CONCEPT_ID
    , CONDITION_CONCEPT_NAME
    , INGREDIENT_CONCEPT_ID
    , INGREDIENT_CONCEPT_NAME
    , AERS
    , AERS_PRR_ORIGINAL
FROM @tableQualifier.PENELOPE_LAERTES_UNI_PIVOT
WHERE CONDITION_CONCEPT_ID IN (@conditionConceptList)
  AND INGREDIENT_CONCEPT_ID IN (@ingredientConceptList)